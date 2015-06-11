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

package org.lappsgrid.serialization

import com.fasterxml.jackson.annotation.JsonIgnore
import org.lappsgrid.serialization.aas.Token

/**
 * @author Keith Suderman
 */
public class Data<T> {
    String discriminator
    T payload
    Map parameters

    public Data() {

    }

    public Data(String discriminator, T payload)
    {
        this.discriminator = discriminator;
        this.payload = payload;
    }

    public Data(String discriminator) {
        this.discriminator = discriminator
    }

    public Data(Map map) {
        this.discriminator = map.discriminator
        this.payload = map.payload
        this.parameters = map.parameters
    }

    @JsonIgnore
    public void setParameter(String name, Object value) {
        if (!parameters) {
            parameters = [:]
        }
        parameters[name] = value
    }

    @JsonIgnore
    public Object getParameter(String name) {
        return parameters?.get(name)
    }

    public String asJson()
    {
        return Serializer.toJson(this);
    }

    public String asPrettyJson()
    {
        return Serializer.toPrettyJson(this);
    }
}
