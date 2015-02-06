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
