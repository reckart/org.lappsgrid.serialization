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
