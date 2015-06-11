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
import static org.junit.Assert.*
import static org.lappsgrid.discriminator.Discriminators.Uri

/**
 * @author Keith Suderman
 */
class ErrorTests {
    public static final String message = "message"

    @Test
    void testConstructors() {
        Error e = new Error()
        assertEquals e.discriminator, Uri.ERROR
        assertNotNull e.payload

        e = new Error(message)
        assertEquals e.discriminator, Uri.ERROR
        assertEquals e.payload, message

        def map = [discriminator: "this is wrong", payload: message]
        e = new Error(map)
        assertEquals e.discriminator, Uri.ERROR
        assertEquals e.payload, message

        e = new Error(payload: message)
        assertEquals e.discriminator, Uri.ERROR
        assertEquals e.payload, message
    }

    @Test
    void testSerialization() {
        Error before = new Error(message)
        String json = Serializer.toJson(before)
        Error after = Serializer.parse(json, Error)
        assertNotNull after
        assertNotNull after.discriminator
        assertNotNull after.payload
        assertEquals before.discriminator, Uri.ERROR
        assertEquals after.discriminator, before.discriminator
        assertEquals before.payload, after.payload
    }
}
