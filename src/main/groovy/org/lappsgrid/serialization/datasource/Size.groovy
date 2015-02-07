package org.lappsgrid.serialization.datasource

import org.lappsgrid.discriminator.Discriminators
import org.lappsgrid.serialization.Data

/**
 * @author Keith Suderman
 */
class Size extends Data<Void> {

    public Size() {
        super(Discriminators.Uri.SIZE)
    }

}
