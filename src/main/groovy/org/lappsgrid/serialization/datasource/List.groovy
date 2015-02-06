package org.lappsgrid.serialization.datasource

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lappsgrid.discriminator.Constants
import org.lappsgrid.serialization.Data
import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
class List extends Data<Offsets> {

    public List() {
        super(Constants.Uri.LIST)
        this.payload = new Offsets();
    }

    public List(int start, int end) {
        super(Constants.Uri.LIST)
        this.payload = new Offsets(start, end)
    }

    @JsonIgnore
    public int getStart() {
        return this.payload.start
    }

    @JsonIgnore
    public int getEnd() {
        return this.payload.end
    }

    public class Offsets {
        int start = -1
        int end = -1

        public Offsets() { }
        public Offsets(int start, int end) {
            this.start = start
            this.end = end
        }
    }
}
