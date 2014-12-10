package org.lappsgrid.serialization.datasource

import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
class GetMetadata extends Query {
    public GetMetadata(Token token) {
        super(token)
    }
}
