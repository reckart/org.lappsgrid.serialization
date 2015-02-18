package org.lappsgrid.serialization.service

import org.lappsgrid.discriminator.Discriminators
import org.lappsgrid.serialization.Data
import org.lappsgrid.serialization.lif.Container

/**
 * @author Keith Suderman
 */
class ExecuteRequest extends Data<Container> {
    public ExecuteRequest() { }
    public ExecuteRequest(Container container) {
        super(Discriminators.Uri.JSON_LD, container)
    }
}
