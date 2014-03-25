package org.anc.lapps.serialization

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

/**
 * Container objects associate a body of text with any annotations that have
 * been created for that text.
 *
 * This is the object that will eventually be serialized over the wire.
 *
 * @author Keith Suderman
 */
class Container {
    /** The text that is to be annotated. */
    String text

    /** Any meta-data attached to this container. */
    Map metadata = [:]

    /** The list of annotations that have been created for the text. */
    List<ProcessingStep> steps = []

    /** Default (empty) constructor. Does nothing. */
    public Container() {}

    /** Constructs a Container object from the values stored in the Map. */
    public Container(Map map) {
        initFromMap(map)
    }

    /** Constructs a Container object from the JSON representation. */
    public Container(String json) {
        Map map = new JsonSlurper().parseText(json)
        initFromMap(map)
    }


    String toJson() {
        return new JsonLd(this).toString()
    }

    String toPrettyJson() {
        return new JsonLd(this).toPrettyString()
    }

    /** Calls toPrettyJson() */
    String toString() {
        return new JsonLd(this).toPrettyString()
    }

    private void initFromMap(Map map) {
        this.text = map.text
        map.metadata.each { name, value ->
            this.metadata[name] = value
        }
        map.steps.each { step ->
            ProcessingStep processingStep = new ProcessingStep()
            step.metadata.each { key,value ->
                processingStep.metadata[key] = value
            }
            step.annotations.each { annotation ->
                processingStep.annotations << new Annotation(annotation)
            }
            this.steps << processingStep
        }
    }

}
