package org.lappsgrid.serialization.datasource

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lappsgrid.discriminator.Discriminators
import org.lappsgrid.serialization.Data

/**
 * @author Keith Suderman
 */
class Get extends Data<String> {

    public Get() {
        super(Discriminators.Uri.GET)
    }

    public Get(String key) {
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
