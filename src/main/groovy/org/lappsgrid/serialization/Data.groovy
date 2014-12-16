package org.lappsgrid.serialization

import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
public class Data<T> {
    String discriminator
    Token token
    T payload

    public Data() {

    }

    public Data(String discriminator, Token token = null, T payload = null) {
        this.discriminator = discriminator
        this.token = token
        this.payload = payload
    }

    public Data(Map map) {
        this.discriminator = map.discriminator
        this.token = map.token
        this.payload = map.payload
    }
}
