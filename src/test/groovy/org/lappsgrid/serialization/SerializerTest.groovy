package org.lappsgrid.serialization

import org.junit.Test
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.datasource.Get

import static org.junit.Assert.*

import java.util.concurrent.TimeUnit

/**
 * @author Keith Suderman
 */
class SerializerTest {

    @Test
    void tokenTest() {
        println "SerializerTest.tokenTest"
        Token before = TokenFactory.create()
        String json = Serializer.toJson(before)
        Token after = Serializer.parse(json, Token)

        compareTokens(before, after)
        println Serializer.toPrettyJson(after)
    }

    @Test
    void getTest() {
        println "SerializerTest.getTest"
        Token beforeToken = TokenFactory.create()
        Get before = new Get(beforeToken, "key")
        String json = Serializer.toJson(before)
        Get after = Serializer.parse(json, Get)
        compareTokens(before.token, after.token)
        assertTrue "Discriminators do not match", before.discriminator == after.discriminator
        assertTrue "Keys do not match", before.key == after.key
        println Serializer.toPrettyJson(after)
    }


    void compareTokens(Token before, Token after) {
        assertTrue "UUIDs do not match", before.uuid == after.uuid
        assertTrue "Issuers do not match", before.issuer == after.issuer
        assertTrue "Timestamps do not match", before.timestamp == after.timestamp
        assertTrue "Lifetimes do not match", before.lifetime == after.lifetime
    }

}
