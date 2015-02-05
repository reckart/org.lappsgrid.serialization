package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.serialization.lif.ValueObject

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class ValueObjectTest {

    @Test
    void testDefaultConstructor() {
        ValueObject object = new ValueObject()
        assertNull(object.type)
        assertNull(object.value)
    }

    @Test
    void testParameterizedConstructor() {
        ValueObject object = new ValueObject('type', 'value')
        assertTrue('type' == object.type)
        assertTrue('value' == object.value)
    }

    @Test
    void testMapConstructor() {
        ValueObject object = new ValueObject(type:'type', value:'value')
        assertTrue('type' == object.type)
        assertTrue('value' == object.value)
    }

    @Test
    void testSerializer() {
        ValueObject before = new ValueObject('type', 'value')
        String json = Serializer.toJson(before)
        ValueObject after = Serializer.parse(json, ValueObject)
        assertTrue before.type == 'type'
        assertTrue before.value == 'value'
        assertTrue before.type == after.type
        assertTrue before.value == after.value
    }
}
