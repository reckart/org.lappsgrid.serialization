package org.lappsgrid.serialization

import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

/**
 * @author Keith Suderman
 */
class DataTests {

    static final String discriminator = "discriminator"
    static final String payload = "payload"

    @Test
    void testConstructors() {
        new Data<String>()
        Data<String> data = new Data<String>(discriminator, payload)
        assertEquals data.discriminator, discriminator
        assertEquals data.payload, payload

        Map map = [discriminator:discriminator, payload:payload]
        data = new Data<String>(map)
        assertEquals data.discriminator, discriminator
        assertEquals data.payload, payload

        data = new Data<String>(discriminator: discriminator, payload: payload)
        assertEquals data.discriminator, discriminator
        assertEquals data.payload, payload
    }

    @Test
    void testSerialization() {
        Data<String> before = new Data<String>(discriminator, payload)
        assertEquals before.discriminator, discriminator
        assertEquals before.payload, payload

        String json = Serializer.toPrettyJson(before)
        Data<String> after = Serializer.parse(json, Data)
        assertNotNull after
        assertNotNull after.discriminator
        assertNotNull after.payload
        assertEquals before.discriminator, after.discriminator
        assertEquals before.payload, after.payload
    }

    @Test
    void testParameters() {
        Data<String> before = new Data<String>(discriminator, payload)
        before.setParameter("p1", "value1")
        before.setParameter("p2", "value2")
        String json = Serializer.toJson(before)
        Data<String> after = Serializer.parse(json, Data)
        assertNotNull after
        assertNotNull after.discriminator
        assertNotNull after.payload
        assertNotNull after.parameters
        assertEquals before.discriminator, after.discriminator
        assertEquals before.payload, after.payload
        assertEquals before.parameters.p1, after.parameters.p1
        assertEquals before.parameters.p2, after.parameters.p2
    }
}
