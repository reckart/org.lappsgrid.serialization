package org.lappsgrid.serialization.datasource

import org.lappsgrid.serialization.Token
import org.lappsgrid.discriminator.Constants

/**
 * @author Keith Suderman
 */
class Get extends Query {
    String key

    public Get() {
        super(Constants.Uri.GET)
    }

    public Get(Token token, String key) {
        super(Constants.Uri.GET, token)
        this.key = key
    }
}
