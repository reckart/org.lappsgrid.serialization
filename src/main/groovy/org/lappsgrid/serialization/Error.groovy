package org.lappsgrid.serialization

import org.lappsgrid.discriminator.Constants

/**
 * @author Keith Suderman
 */
class Error extends Data<String> {

    public Error() {
        super(Constants.Uri.ERROR, "Unknown error.")
    }

    public Error(Map map) {
        this.discriminator = map['discriminator']
        this.payload = map['payload']
    }

    public Error(String message) {
        this.discriminator = Constants.Uri.ERROR
        this.payload = message
    }
}
