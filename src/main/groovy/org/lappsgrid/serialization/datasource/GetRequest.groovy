package org.lappsgrid.serialization.datasource

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lappsgrid.discriminator.Discriminators
import org.lappsgrid.serialization.Data

/**
 * @author Keith Suderman
 */
class GetRequest extends Data<String> {

    public GetRequest() {
        super(Discriminators.Uri.GET)
    }

    public GetRequest(String key) {
        super(Discriminators.Uri.GET, key)
    }

    @JsonIgnore
    public String getKey() {
        return payload
    }

    public void setKey(String key) {
        this.payload = key
    }
}
