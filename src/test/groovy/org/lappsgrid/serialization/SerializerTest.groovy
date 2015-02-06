package org.lappsgrid.serialization

import org.junit.Test
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.datasource.Get

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class SerializerTest {

    @Test
    void getTest() {
        println "SerializerTest.getTest"
        Get before = new Get("key")
        String json = Serializer.toJson(before)
        Get after = Serializer.parse(json, Get)
        assertTrue "Discriminators do not match", before.discriminator == after.discriminator
        assertTrue "Keys do not match", before.key == after.key
        println Serializer.toPrettyJson(after)
    }
    
}
