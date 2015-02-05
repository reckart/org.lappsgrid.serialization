package org.lappsgrid.serialization

import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
public class Data<T> {
    String discriminator
    T payload

    public Data() {

    }

    public Data(String discriminator, T payload)
    {
        this.discriminator = discriminator;
        this.payload = payload;
    }

    public Data(String discriminator) {
        this.discriminator = discriminator
    }

    public Data(Map map) {
        this.discriminator = map.discriminator
        this.payload = map.payload
    }

    public String asJson()
    {
        return Serializer.toJson(this);
    }

    public String asPrettyJson()
    {
        return Serializer.toPrettyJson(this);
    }
}
