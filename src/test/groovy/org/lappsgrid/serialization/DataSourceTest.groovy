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
        Token token = TokenFactory.create();
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
        List before = new List(TokenFactory.create())
        String json = Serializer.toPrettyJson(before)
//        println json
        List after = Serializer.parse(json, List)
        assertTrue after.discriminator == Constants.Uri.LIST
        assertTrue after.discriminator == before.discriminator
        compareTokens(before.token, after.token)
        assertTrue after.start == -1
        assertTrue after.end == -1
        assertTrue before.start == after.start
        assertTrue before.end == after.end
    }

    @Test
    void testListIndexed() {
        int start = 1
        int end = 2
        println "DataSourceTest.testListIndexed"
        List before = new List(TokenFactory.create(), start, end)
        String json = Serializer.toPrettyJson(before)
        List after = Serializer.parse(json, List)
        assertTrue after.discriminator == Constants.Uri.LIST
        assertTrue before.discriminator == after.discriminator
        assertTrue before.token.uuid == after.token.uuid

    }

    void compareTokens(Token t1, Token t2) {
        assertTrue 'Token UUIDs do not match', t1.uuid == t2.uuid
        assertTrue 'Token issuers do not match', t1.issuer == t2.issuer
        assertTrue 'Token timestamps do not match', t1.timestamp == t2.timestamp
        assertTrue 'Token lifetimes do not match', t1.lifetime == t2.lifetime
    }
}
