package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.discriminator.Constants
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
        Token token = TokenFactory.createToken();
        Get before = new Get(token, key)
        String json = Serializer.toPrettyJson(before)
//        println json
        Get after = Serializer.parse(json, Get)
        assertTrue after.discriminator == Constants.Uri.GET
        assertTrue before.discriminator == after.discriminator
        compareTokens(before.token, after.token)
        assertTrue before.key == after.key
    }

    @Test
    void testList() {
        println "DataSourceTest.testList"
        List before = new List(TokenFactory.createToken())
        String json = Serializer.toPrettyJson(before)
        println json
        List after = Serializer.parse(json, List)
        assertTrue after.discriminator == Constants.Uri.LIST
        assertTrue after.discriminator == before.discriminator
        compareTokens(before.token, after.token)
        assertTrue after.payload.start == -1
        assertTrue after.payload.end == -1
        assertTrue before.payload.start == after.payload.start
        assertTrue before.payload.end == after.payload.end
    }

    @Test
    void testListIndexed() {
        int start = 1
        int end = 2
        println "DataSourceTest.testListIndexed"
        List before = new List(TokenFactory.createToken(), start, end)
        String json = Serializer.toPrettyJson(before)
        List after = Serializer.parse(json, List)
        assertTrue after.discriminator == Constants.Uri.LIST
        assertTrue before.discriminator == after.discriminator
        assertTrue before.token.uuid == after.token.uuid
        assertTrue after.payload.start == start
        assertTrue after.payload.end == end
    }

    @Test
    void testAsMap() {
        println "DataSourceTest.testAsMap"
        int start = 1
        int end = 2
        Get before = new Get(TokenFactory.createToken(), 'key')
        String json = Serializer.toPrettyJson(before)
        Map map = Serializer.parse(json, Map)
        if (map.discriminator) {
            if (map.discriminator == Constants.Uri.GET) {
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

    void compareTokens(Token t1, Token t2) {
        assertTrue 'Token UUIDs do not match', t1.uuid == t2.uuid
        assertTrue 'Token issuers do not match', t1.issuer == t2.issuer
        assertTrue 'Token timestamps do not match', t1.timestamp == t2.timestamp
        assertTrue 'Token lifetimes do not match', t1.lifetime == t2.lifetime
    }
}
