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

package org.lappsgrid.serialization.aas

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author Keith Suderman
 */
@JsonPropertyOrder(['uuid', 'issuer', 'timestamp', 'lifetime'])
class Token {
    String uuid
    String issuer
    long timestamp
    long lifetime

//    public Token(String json) {
//        ObjectMapper mapper = new ObjectMapper()
//        Token proxy = (Token) mapper.readValue(json, Token)
//        this.uuid = proxy.uuid
//        this.issuer = proxy.issuer
//        this.timestamp = proxy.timestamp
//        this.lifetime = proxy.lifetime
//    }

    boolean expired() {
        return timestamp + lifetime < System.currentTimeMillis()
    }
}
