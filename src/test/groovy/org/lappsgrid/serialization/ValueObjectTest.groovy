/*
 * Copyright 2014 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
