package org.lappsgrid.serialization.service

import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
abstract class ServiceCall {
    String discriminator
    Token token

    public ServiceCall() { }
    public ServiceCall(String discriminator, Token token) {
        this.discriminator = discriminator
        this.token = token
    }
}
