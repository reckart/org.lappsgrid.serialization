/*-
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
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * Information about a standoff annotation.
 *
 * @author Keith Suderman
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder(['id', 'start', 'end', '@type', 'type', 'label', 'features', 'metadata'])
public class Annotation {
    /** A unique ID assigned to this annotation.
     * <p>
     * <em>Note:</em> This ID value is assigned to the annotation by the framework
     * and is not to be confused with an ID (xml:id, etc) value that the annotation
     * itself might contain.
     */
    String id

    /** The label used for the annotation, e.g. tok, s, etc. */
//    @JsonProperty('@type')
    String label

    @JsonProperty('@type')
    String atType

    String type

    /** The start offset of the annotation. */
    Long start = null

    /** The end offset of the annotation. */
    Long end = null

    /** Features of the annotation. */
    Map features = [:]

    /** Features assigned by the framework to the annotation. E.g. a confidence
     * score, the processor that generated the annotation etc.
     */
    Map metadata = [:]

    public Annotation() { }

    public Annotation(String label, long start, long end) {
        this.label = label
        this.start = start
        this.end = end
    }

    public Annotation(Map map) {
        map.each { key, value ->
            switch(key) {
                case 'label':
                    this.label = value
                    break
                case '@type':
                    this.atType = value
                    break
                case 'type':
                    this.type = value
                    break
                case 'start':
                    this.start = value as Long
                    break
                case 'end':
                    this.end = value as Long
                    break
                case 'id':
                    this.id = value
                    break
                case 'features':
                    this.features << value
                    break
                case 'metadata':
                    this.metadata << value
                    break
                default:
                    //println "${key} = ${value}"
                    features[key] = value
                    break
            }
        }
    }

//    @JsonIgnore
//    void setLabel(String label) {
//        this.label = label
//    }
//
//    @JsonIgnore
//    String getLabel() { return this.type }

    @JsonIgnore
    void addFeature(String name, String value) {
        features[name] = value
    }

    @JsonIgnore
    String getFeature(String name) {
        return features[name]
    }

    String toString() {
        return "${label} (${start}-${end}) ${features}"
    }

}
