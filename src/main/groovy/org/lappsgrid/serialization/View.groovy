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

import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * A View consists of some metadata and a list of annotations.
 * <p>
 * Each LAPPS processing service will generally place the annotations it generates in
 * its own View object.  This makes it easier to determine the annotations
 * produces by each processor and to quickly extract that subset of annotations.
 * <p>
 * However, the concept of a View is meant to be very generic and can contain
 * any arbitrary collection of annotations grouped with arbitrary metadata.
 *
 * @author Keith Suderman
 */
@JsonPropertyOrder(['id', 'metadata', 'annotations'])
public class View {
    /**
     * A unique ID value for this view.
     */
    String id

    /**
     * User defined metadata for this processing step.
     */
    Map metadata

    /**
     * The annotations that belong to this processing step.
     */
    List<Annotation> annotations

    public View() {
        metadata = [:]
        annotations = []
    }

    public View(Map map) {
        metadata = map.metadata
        annotations = map.annotations
    }

    /**
     * Adds the name/value pair to the metadata map.
     *
     */
    void addMetaData(String name, Object value) {
        metadata[name] = value
    }

    /**
     * Adds an annotation to the processing step's list of annotations.
     */
    void addAnnotation(Annotation annotation) {
        annotations << annotation
    }

    /**
     * Adds an annotation to the processing step's list of annotations.
     */
    void add(Annotation annotation) {
        annotations << annotation
    }

    /**
     * Returns true if the metadata.contains map contains the named key. Returns
     * false otherwise.
\    */
    boolean contains(String name) {
        return metadata.contains[name] != null
    }

    /**
     * Creates and returns a new Annotation.
     */
    Annotation newAnnotation() {
        Annotation a = new Annotation()
        annotations.add(a)
        return a
    }

    /**
     * Creates and returns a new Annotation.
     */
    Annotation newAnnotation(String type, long start, long end) {
        Annotation a = newAnnotation()
        a.setType(type)
        a.setStart(start)
        a.setEnd(end)
        return a
    }

    Contains getContains(String name) {
        return metadata?.contains[name]
    }

    void addContains(String name, String producer, String type) {
//        ValueObject containsType = new ValueObject(type:type, value:value)
        if (metadata.contains == null) {
            metadata.contains = [:]
        }
        metadata.contains[name] = new Contains(producer:producer, type:type)
    }

}
