package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.service.Execute

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class ServiceCallTest {

    @Ignore
    void testExecute() {
        println "ServiceCallTest.testExecute"
        Token token = TokenFactory.createToken()
        Container container = ContainerFactory.createContainer()
        Execute before = new Execute(token, container)
        String json = Serializer.toPrettyJson(before)
        println json
        Execute after = Serializer.parse(json, Execute)
        assertTrue before.token.uuid == after.token.uuid
        assertTrue before.token.timestamp == after.token.timestamp
        assertTrue before.container.text == after.container.text
    }

    @Ignore
    void testExecuteNoToken() {
        println "ServiceCallTest.testExecuteNoToken"
        Container container = ContainerFactory.createContainer();
        Execute before = new Execute(null, container)
        assertNull before.token
        String json = Serializer.toPrettyJson(before)
        //println json
        Execute after = Serializer.parse(json, Execute)
        assertTrue before.discriminator == after.discriminator
        assertNull after.token
        compareTokens(before.token, after.token)
    }

    @Test
    void testExecuteFromMap() {
        println "ServiceCallTest.testExecuteFromMap"
        Token token = TokenFactory.createToken()
        Container c1 = ContainerFactory.createContainer()
        Execute execute = new Execute(token, c1)
        String json = Serializer.toPrettyJson(execute)
//        println json
        Map map = Serializer.parse(json, Map)
//        println "Discriminator is ${map.discriminator}"
        Container c2 = new Container(map.payload)
        println Serializer.toPrettyJson(c2)
//        println c1.language
//        println c2.language
        assertTrue c1.language == c2.language
        assertTrue c1.text == c2.text
        assertTrue c1.views.size() == c2.views.size()
        assertTrue c1.metadata.size() == c2.metadata.size()
        c1.metadata.each { name, value ->
            assertTrue c2.metadata[name] == value
        }
//        def payload = map.payload
//        println "Payload is a ${payload.class}"
    }

    void compareTokens(Token t1, Token t2) {
        assertTrue 'Token UUIDs do not match', t1.uuid == t2.uuid
        assertTrue 'Token issuers do not match', t1.issuer == t2.issuer
        assertTrue 'Token timestamps do not match', t1.timestamp == t2.timestamp
        assertTrue 'Token lifetimes do not match', t1.lifetime == t2.lifetime
    }
}
