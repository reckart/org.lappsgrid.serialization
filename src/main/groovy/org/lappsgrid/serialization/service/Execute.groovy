package org.lappsgrid.serialization.service

import org.lappsgrid.discriminator.Discriminators
import org.lappsgrid.serialization.Data
import org.lappsgrid.serialization.lif.Container

/**
 * @author Keith Suderman
 */
class Execute extends Data<Container> {
    public Execute() { }
    public Execute(Container container) {
        super(Discriminators.Uri.JSON_LD, container)
    }
}
