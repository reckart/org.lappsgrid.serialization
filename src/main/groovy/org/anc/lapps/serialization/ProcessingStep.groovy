package org.anc.lapps.serialization

import com.fasterxml.jackson.annotation.JsonPropertyOrder

/**
 * A ProcessingStep consists of some metadata and a list of annotations.
 * <p>
 * Each LAPPS processing service will generally place the annotations it generates in
 * its own ProcessingStep object.  This makes it easier to determine the annotations
 * produces by each processor and to quickly extract that subset of annotations.
 *
 * @author Keith Suderman
 */
@JsonPropertyOrder(['metadata', 'annotations'])
public class ProcessingStep {
    /**
     * User defined metadata for this processing step.
     */
    Map metadata

    /**
     * The annotations that belong to this processing step.
     */
    List<Annotation> annotations

    public ProcessingStep() {
        metadata = [:]
        annotations = []
    }

    public ProcessingStep(Map map) {
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
