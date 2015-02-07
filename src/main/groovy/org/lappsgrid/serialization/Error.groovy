package org.lappsgrid.serialization

import static org.lappsgrid.discriminator.Discriminators.Uri

/**
 * @author Keith Suderman
 */
class Error extends Data<String> {

    public Error() {
        super(Uri.ERROR, "Unknown error.")
    }

    public Error(Map map) {
        this.discriminator = map['discriminator']
        this.payload = map['payload']
    }

    public Error(String message) {
        this.discriminator = Uri.ERROR
        this.payload = message
    }
}
