package org.lappsgrid.serialization.datasource

import org.lappsgrid.serialization.Token

/**
 * @author Keith Suderman
 */
abstract class Query {
    String discriminator
    Token token

    protected Query(String discriminator) {
        this.discriminator = discriminator
    }

    protected Query(String discriminator, Token token) {
        this.discriminator = discriminator
        this.token = token
    }
}
