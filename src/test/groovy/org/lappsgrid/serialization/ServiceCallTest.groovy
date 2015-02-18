package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.service.ExecuteRequest

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class ServiceCallTest {

    @Test
    void testExecute() {
        println "ServiceCallTest.testExecute"
        Container container = ContainerFactory.createContainer()
        ExecuteRequest before = new ExecuteRequest(container)
        String json = before.asJson() //Serializer.toPrettyJson(before)
        println json
        ExecuteRequest after = Serializer.parse(json, ExecuteRequest)

        compareContainers(before.payload, after.payload)
    }

    @Test
    void testExecuteNoToken() {
        println "ServiceCallTest.testExecuteNoToken"
        Container container = ContainerFactory.createContainer();
        ExecuteRequest before = new ExecuteRequest(container)
        String json = before.asJson()
        ExecuteRequest after = Serializer.parse(json, ExecuteRequest)
        assertTrue before.discriminator == after.discriminator
        compareContainers before.payload, after.payload
    }

    @Test
    void testExecuteFromMap() {
        println "ServiceCallTest.testExecuteFromMap"
        Container c1 = ContainerFactory.createContainer()
        ExecuteRequest execute = new ExecuteRequest(c1)
        String json = execute.asJson()
        Map map = Serializer.parse(json, Map)
        Container c2 = new Container(map.payload)
        println Serializer.toPrettyJson(c2)
        assertTrue c1.language == c2.language
        assertTrue c1.text == c2.text
        assertTrue c1.views.size() == c2.views.size()
        assertTrue c1.metadata.size() == c2.metadata.size()
        compareMaps c1.metadata, c2.metadata
    }

    void compareContainers(Container c1, Container c2) {
        assertEquals c1.content.value, c2.content.value
        assertEquals c1.content.language, c2.content.language
        assertEquals c1.text, c2.text
        assertEquals c1.language, c2.language
        assertEquals c1.views.size(), c2.views.size()
        compareMaps c1.metadata, c2.metadata
    }

    void check(String message, def expected, def actual) {
        if (expected != actual) {
            fail "${message}: Expect $expected Found $actual"
        }
    }

    void compareMaps(Map map1, Map map2) {
        assertTrue map1.size() == map2.size()
        map1.each { key, value ->
            Object object = map2[key]
            assertNotNull "Expected a value for ${key}", object
            if (value instanceof Map) {
                if (object instanceof Map) {
                    compareMaps(value, (Map)object)
                }
                else {
                    fail "Expected Map for ${key} Found ${object.class.name}"
                }
            }
            else if (value instanceof Collection) {
                if (object instanceof Collection) {
                    compareCollections((Collection)value, (Collection)object)
                }
                else {
                    fail "${key} expected Collection Found ${object.class.name}"
                }
            }
            else {
                assertTrue "$key Expected $value Found $object", value == object
            }
        }
    }

    void compareCollections(Collection c1, Collection c2) {
        assertTrue c1.size() == c2.size()
        c1.each { assertTrue c2.contains(it) }
        c2.each { assertTrue c1.contains(it) }
    }

}
