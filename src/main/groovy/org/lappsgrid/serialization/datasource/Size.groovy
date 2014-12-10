package org.lappsgrid.serialization.datasource

import org.lappsgrid.discriminator.Constants
import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
class Size extends Query {

    public Size(Token token) {
        super(Constants.Uri.SIZE, token)
    }
}
