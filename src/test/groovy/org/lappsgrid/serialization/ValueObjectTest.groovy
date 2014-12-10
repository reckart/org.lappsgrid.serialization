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
}
