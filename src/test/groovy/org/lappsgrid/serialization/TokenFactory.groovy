package org.lappsgrid.serialization

import org.lappsgrid.serialization.aas.Token

import java.util.concurrent.TimeUnit

/**
 * @author Keith Suderman
 */
class TokenFactory {
    public static Token create() {
        Token token = new Token()
        token.uuid = UUID.randomUUID().toString()
        token.issuer = "http://www.anc.org"
        token.timestamp = System.currentTimeMillis()
        token.lifetime = TimeUnit.DAYS.toMillis(1L)
        return token
    }
}
