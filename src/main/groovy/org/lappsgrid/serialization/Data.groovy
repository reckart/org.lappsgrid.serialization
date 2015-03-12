package org.lappsgrid.serialization

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
public class Data<T> {
    String discriminator
    T payload
    Map parameters

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
        this.parameters = map.parameters
    }

    @JsonIgnore
    public void setParameter(String name, Object value) {
        if (!parameters) {
            parameters = [:]
        }
        parameters[name] = value
    }

    @JsonIgnore
    public Object getParameter(String name) {
        return parameters?.get(name)
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
