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
import com.fasterxml.jackson.databind.SerializationFeature

/**
 * @author Keith Suderman
 */
@JsonPropertyOrder(['username','password','resourceId'])
class LoginRequest {
    private final ObjectMapper mapper
    String username
    String password
    String resourceId

    public LoginRequest() {
        mapper = new ObjectMapper()
    }

    public LoginRequest(String username, String password, String resourceId) {
        this()
        this.username = username
        this.password = password
        this.resourceId = resourceId
    }

    public LoginRequest(Map parameters) {
        this()
        this.username = parameters.username
        this.password = parameters.password
        this.resourceId = parameters.resourceId
    }

    public LoginRequest(String json) {
        this()
        LoginRequest proxy = new ObjectMapper().readValue(json, LoginRequest)
        this.username = proxy.username
        this.password = proxy.password
        this.resourceId = proxy.resourceId
    }

    String toJson() {
        mapper.disable(SerializationFeature.INDENT_OUTPUT)
        return mapper.writeValueAsString(this)
    }

    String toPrettyJson() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        return mapper.writeValueAsString(this)
    }

    /** Calls toPrettyJson() */
    String toString() {
        return toJson()
    }
}
