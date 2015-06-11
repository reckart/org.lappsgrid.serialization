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
