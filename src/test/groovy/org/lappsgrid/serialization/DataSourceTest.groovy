package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.serialization.datasource.Size

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
        assertEquals after.discriminator, Uri.GET
        assertEquals before.discriminator, after.discriminator
        assertEquals before.key, after.key
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

    @Test
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

    @Test
    void testGetAsMap() {
        println "DataSourceTest.testGetAsMap"
        int start = 1
        int end = 2
        Get before = new Get('key')
        String json = Serializer.toPrettyJson(before)
        Map map = Serializer.parse(json, Map)
        assertNotNull(map)
        assertNotNull(map.discriminator)
        assertEquals map.discriminator, Uri.GET
    }

    @Test
    void testListAsMap() {
        println "DataSourceTest.testListAsMap"
        List before = new List(1,2)
        String json = Serializer.toPrettyJson(before)
        Map map = Serializer.parse(json, HashMap)
        assertNotNull map
        assertNotNull map.discriminator
        assertEquals map.discriminator, Uri.LIST
        assertEquals map.payload.start, 1
        assertEquals map.payload.end, 2
    }

    @Test
    void testSize() {
        println "DataSourceTest.testSize"
        Size before = new Size()
        String json = Serializer.toPrettyJson(before)
        Size after = Serializer.parse(json, Size)
        assertEquals before.discriminator, Uri.SIZE
        assertEquals before.discriminator, after.discriminator
        assertEquals before.payload, null
    }
}
