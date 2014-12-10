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

    @Test
    void testExecute() {
        println "ServiceCallTest.testExecute"
        Token token = TokenFactory.create()
        Container container = ContainerFactory.create()
        Execute before = new Execute(token, container)
        String json = Serializer.toPrettyJson(before)
        println json
        Execute after = Serializer.parse(json, Execute)
        assertTrue before.token.uuid == after.token.uuid
        assertTrue before.token.timestamp == after.token.timestamp
        assertTrue before.container.text == after.container.text
    }

    @Test
    void testExecuteNoToken() {
        println "ServiceCallTest.testExecuteNoToken"
        Container container = ContainerFactory.create();
        Execute before = new Execute(null, container)
        assertNull before.token
        String json = Serializer.toPrettyJson(before)
        //println json
        Execute after = Serializer.parse(json, Execute)
        assertTrue before.discriminator == after.discriminator
        assertNull after.token
    }
}
