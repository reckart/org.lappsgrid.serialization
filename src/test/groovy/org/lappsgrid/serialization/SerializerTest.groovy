package org.lappsgrid.serialization

import org.junit.Test
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.datasource.Get
import org.lappsgrid.serialization.datasource.List
import org.lappsgrid.serialization.datasource.Size
import static org.lappsgrid.discriminator.Discriminators.Uri

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
@Mixin(Check)
class SerializerTest {

    @Test
    void getTest() {
        println "SerializerTest.getTest"
        Get before = new Get("key")
        String json = Serializer.toJson(before)
        Get after = Serializer.parse(json, Get)
        check(before.discriminator, Uri.GET)
        check(before.discriminator, after.discriminator)
        check(before.key, after.key)
        println Serializer.toPrettyJson(after)
    }

    @Test
    void listTest() {
        println "SerializerTest.listTest"
        List before = new List();
        String json = Serializer.toPrettyJson(before)
        List after = Serializer.parse(json, List)
        check before.discriminator, Uri.LIST
        check before.discriminator, after.discriminator
        check before.start, -1
        check before.end, -1
        check before.start, after.start
        check before.end, after.end
    }

    @Test
    void listOffsetTest() {
        println "SerializerTest.listOffsetTest"
        List before = new List(1,2)
        String json = Serializer.toJson(before)
        List after = Serializer.parse(json, List)
        check before.discriminator, Uri.LIST
        check before.discriminator, after.discriminator
        check before.start, 1
        check before.end, 2
        check before.start, after.start
        check before.end, after.end
    }

    @Test
    void sizeTest() {
        println "SerializerTest.sizeTest"
        Size before = new Size()
        String json = Serializer.toPrettyJson(before)
        Size after = Serializer.parse(json, Size)
        check before.discriminator, Uri.SIZE
        check before.discriminator, after.discriminator
        check before.payload, null
    }
}
