package org.lappsgrid.serialization.datasource

import org.lappsgrid.discriminator.Constants

/**
 * @author Keith Suderman
 */
class List extends Query {
    int start
    int end

    public List() {
        super(Constants.Uri.LIST)
    }
}
