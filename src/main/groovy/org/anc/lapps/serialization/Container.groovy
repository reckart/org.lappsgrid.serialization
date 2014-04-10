package org.anc.lapps.serialization

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
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
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
class Container {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Content {
        @JsonProperty('@value')
        String value
        @JsonProperty('@language')
        String language
        public Content() { }
    }

    @JsonProperty('@type')
    String type = 'textAnnotation'

    /** The text that is to be annotated. */
    @JsonProperty('text')
    Content content = new Content()

    /** Any meta-data attached to this container. */
    Map metadata = [:]

    /** The list of annotations that have been created for the text. */
    List<ProcessingStep> steps = []

    private static final ObjectMapper mapper = new ObjectMapper();

    @JsonProperty("@context")
    Map context = [
        '@vocab':'http://vocab.lappsgrid.org/',
        'xsd': 'http://www.w3.org/2001/XMLSchema#',
        'meta': [
            '@id': 'http://meta.lappsgrid.org/',
            '@type': '@id'
        ],
        'syntax': [
            '@id': 'http://syntax.lappsgrid.org/',
            '@type': '@id'
        ],
        'metadata': 'syntax:metadata',
        'steps': [ '@id':'syntax:steps', '@container':'@list'],
        'annotations': ['@id':'syntax:annotations', '@container':'@list'],
        'id': ['@id':'syntax:id', '@type':'xsd:string'],
        'start': ['@id':'syntax:start', '@type':'xsd:long'],
        'end': ['@id':'syntax:end', '@type':'xsd:long'],
        'features': ['@id':'syntax:features', '@container':'@set'],

        'annotationInfo': 'meta:annotationInfo',
        'producer': 'meta:producer',
        'url': 'meta:url',
        'tokenType': 'meta:url',
        'posType': 'meta:url',
        'version': 'meta:version'
    ]

    /** Default (empty) constructor. Does nothing. */
    public Container() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /** Constructs a Container object from the values stored in the Map. */
    public Container(Map map) {
        this()
        initFromMap(map)
    }

    /** Constructs a Container object from the JSON representation. */
    public Container(String json) {
        this()
        //Map map = new JsonSlurper().parseText(json)
        //initFromMap(map)
        Container proxy = mapper.readValue(json, Container.class)
        //this.text = new Text()
        //this.text.value = proxy.text.value
        //this.text.language = proxy.text.language
        this.content = proxy.content
        this.metadata = proxy.metadata
        this.steps = proxy.steps
        this.context = proxy.context
    }

    @JsonIgnore
    void setLanguage(String lang) {
        content.language = lang
    }

    @JsonIgnore
    String getLanguage() {
        return content.language
    }

    @JsonIgnore
    void setText(String text) {
        this.content.value = text
    }

    @JsonIgnore
    String getText() {
        return this.content.value
    }

//    private Content getContent() { return null }
//    private void setContent(Content ignored) { }

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
