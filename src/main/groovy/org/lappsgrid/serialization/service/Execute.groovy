package org.lappsgrid.serialization.service

import org.lappsgrid.discriminator.Constants
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.lif.Container

/**
 * @author Keith Suderman
 */
class Execute extends ServiceCall {
    Container container

    public Execute() { }
    public Execute(Token token, Container container) {
        super(Constants.Uri.EXECUTE, token)
        this.container = container
    }
}
