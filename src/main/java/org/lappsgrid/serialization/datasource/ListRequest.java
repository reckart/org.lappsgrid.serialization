/*
 * Copyright 2014 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.lappsgrid.serialization.datasource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.lappsgrid.discriminator.Discriminators;
import org.lappsgrid.serialization.Data;

/**
 * The {@link org.lappsgrid.serialization.Data} object sent to a Datasource to retrieve a list of
 * document IDs from the Datasource.
 * <p>
 * Datasources that contain a large number of documents may refuse to
 * honor this request, in which case the {@code start} and {@code end} offsets
 * should be used to paginate through the index.  Send a {@link SizeRequest} request to
 * determine the total number of documents in the Datasource.
 *
 * @author Keith Suderman
 */
public class ListRequest extends Data<ListRequest.Offsets> {

    public ListRequest() {
        super(Discriminators.Uri.LIST);
        this.payload = new Offsets();
    }

    public ListRequest(int start, int end) {
        super(Discriminators.Uri.LIST);
        this.payload = new Offsets(start, end);
    }

    @JsonIgnore
    public int getStart() {
        return this.payload.start;
    }

    @JsonIgnore
    public int getEnd() {
        return this.payload.end;
    }

    public class Offsets {
        protected int start = -1;
        protected int end = -1;

        public Offsets() { }
        public Offsets(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int getStart()
        {
            return start;
        }

        public void setStart(int start)
        {
            this.start = start;
        }

        public int getEnd()
        {
            return end;
        }

        public void setEnd(int end)
        {
            this.end = end;
        }
    }
}
