package org.lappsgrid.serialization.datasource

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lappsgrid.serialization.Data
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.discriminator.Constants

/**
 * @author Keith Suderman
 */
class Get extends Data<String> {

    public Get() {
        super(Constants.Uri.GET)
    }

    public Get(String key) {
        super(Constants.Uri.GET, key)
    }

    @JsonIgnore
    public String getKey() {
        return payload
    }

    public void setKey(String key) {
        this.payload = key
    }
}
