package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.serialization.datasource.ListRequest

import static org.junit.Assert.*
import static org.lappsgrid.discriminator.Discriminators.Uri

/**
 * @author Keith Suderman
 */
class ListTests {

    @Test
    void testConstructors() {
        ListRequest list = new ListRequest();
        assertTrue list.discriminator == Uri.LIST

        list = new ListRequest(1,2)
        assertEquals list.discriminator, Uri.LIST
        assertEquals list.start, 1
        assertEquals list.end, 2
    }

    @Test
    void testSerialization() {
        ListRequest before = new ListRequest();
        String json = Serializer.toJson(before)
        ListRequest after = Serializer.parse(json, ListRequest)
        assertEquals before.discriminator, Uri.LIST
        assertEquals before.discriminator, after.discriminator

        before = new ListRequest(1,2)
        json = Serializer.toJson(before)
        after = Serializer.parse(json, ListRequest)
        assertEquals before.discriminator, Uri.LIST
        assertEquals before.start, 1
        assertEquals before.end, 2
        assertEquals before.discriminator, after.discriminator
        assertEquals before.start, after.start
        assertEquals before.end, after.end
    }
}
