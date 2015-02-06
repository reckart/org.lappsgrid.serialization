package org.lappsgrid.serialization

import org.junit.*
import static org.lappsgrid.discriminator.Discriminators.Uri
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.datasource.Get
import org.lappsgrid.serialization.datasource.List

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class DataSourceTest {

    @Test
    void testGet() {
        println "DataSourceTest.testGet"
        String key = 'MASC3-0001'
        Get before = new Get(key)
        String json = Serializer.toPrettyJson(before)
//        println json
        Get after = Serializer.parse(json, Get)
        assertNotNull "Unable to deserialize Get", after
        assertNotNull "Mising discriminator", after.discriminator
        assertNotNull "Missing key", after.key
        assertTrue after.discriminator == Uri.GET
        assertTrue before.discriminator == after.discriminator
        assertTrue before.key == after.key
    }

    @Test
    void testList() {
        println "DataSourceTest.testList"
        List before = new List()
        String json = Serializer.toPrettyJson(before)
        List after = Serializer.parse(json, List)
        assertTrue after.discriminator == Uri.LIST
        assertTrue after.discriminator == before.discriminator
        assertTrue after.payload.start == -1
        assertTrue after.payload.end == -1
        assertTrue before.payload.start == after.payload.start
        assertTrue before.payload.end == after.payload.end
    }

    @Ignore
    void testListIndexed() {
        int start = 1
        int end = 2
        println "DataSourceTest.testListIndexed"
        List before = new List(start, end)
        String json = Serializer.toPrettyJson(before)
        List after = Serializer.parse(json, List)
        assertTrue after.discriminator == Uri.LIST
        assertTrue before.discriminator == after.discriminator
        assertTrue after.payload.start == start
        assertTrue after.payload.end == end
    }

    @Ignore
    void testAsMap() {
        println "DataSourceTest.testAsMap"
        int start = 1
        int end = 2
        Get before = new Get('key')
        String json = Serializer.toPrettyJson(before)
        Map map = Serializer.parse(json, Map)
        if (map.discriminator) {
            if (map.discriminator == Uri.GET) {
                println "This is a GET object"
            }
            else {
                println "This is a ${map.discriminator}"
            }
        }
        else {
            println "discriminator is null."
        }

    }

//    void compareTokens(Token t1, Token t2) {
//        assertTrue 'Token UUIDs do not match', t1.uuid == t2.uuid
//        assertTrue 'Token issuers do not match', t1.issuer == t2.issuer
//        assertTrue 'Token timestamps do not match', t1.timestamp == t2.timestamp
//        assertTrue 'Token lifetimes do not match', t1.lifetime == t2.lifetime
//    }
}
