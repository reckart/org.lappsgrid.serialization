package org.lappsgrid.serialization.datasource

import org.lappsgrid.discriminator.Constants
import org.lappsgrid.serialization.Data
import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
class GetMetadata extends Data<Void> {

    public GetMetadata() {
        super(Constants.Uri.GETMETADATA)
    }

    public GetMetadata(Token token) {
        super(Constants.Uri.GETMETADATA, token)
    }
}
