package org.lappsgrid.serialization.datasource

import org.lappsgrid.discriminator.Discriminators
import org.lappsgrid.serialization.Data

/**
 * @author Keith Suderman
 */
class SizeRequest extends Data<Void> {

    public SizeRequest() {
        super(Discriminators.Uri.SIZE)
    }

}
