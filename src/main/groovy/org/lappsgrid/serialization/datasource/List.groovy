package org.lappsgrid.serialization.datasource

import org.lappsgrid.discriminator.Constants
import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
class List extends Query {
    int start = -1
    int end = -1

    public List() {
        super(Constants.Uri.LIST)
    }

    public List(Token token) {
        super(Constants.Uri.LIST, token)
    }

    public List(Token token, int start, int end) {
        this(token)
        this.start = start
        this.end = end
    }

}
