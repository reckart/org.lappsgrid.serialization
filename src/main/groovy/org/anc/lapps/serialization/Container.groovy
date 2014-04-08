package org.anc.lapps.serialization

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
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

    private static final ObjectMapper mapper = new ObjectMapper();

    @JsonProperty("@context")
    Map context = [
            '@vocab': 'http://vocab.lappsgrid.org'
    ]

    /** Default (empty) constructor. Does nothing. */
    public Container() {}

    /** Constructs a Container object from the values stored in the Map. */
    public Container(Map map) {
        initFromMap(map)
    }

    /** Constructs a Container object from the JSON representation. */
    public Container(String json) {
        //Map map = new JsonSlurper().parseText(json)
        //initFromMap(map)
        Container proxy = mapper.readValue(json, Container.class)
        this.text = proxy.text
        this.metadata = proxy.metadata
        this.steps = proxy.steps
        this.context = proxy.context
    }

    void define(String name, String iri) {
        if (this.context[name]) {
            throw new LappsIOException("Context for ${name} already defined.")
        }
        this.context[name] = [
            '@id': iri,
            '@type': '@id'
        ]
    }

    String toJson() {
        mapper.disable(SerializationFeature.INDENT_OUTPUT)
        return mapper.writeValueAsString(this)
        //return new JsonLd(this).toString()
    }

    String toPrettyJson() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        return mapper.writeValueAsString(this)
        //return new JsonLd(this).toPrettyString()
    }

    /** Calls toPrettyJson() */
    String toString() {
        //return new JsonLd(this).toPrettyString()
        return toJson()
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
