package org.anc.lapps.serialization

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

/**
 * Container objects associate a body of text with any annotations that have
 * been created for that text.
 *
 * This is the object that will eventually be serialized over the wire.
 *
 * @author Keith Suderman
 */
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder(["context","metadata","text","steps"])
public class Container {

    /** The text that is to be annotated. */
    @JsonProperty('text')
    Content content;
//    Map<String,String> content = [:]

    /** Any meta-data attached to this container. */
    Map metadata // = [:]

    /** The list of annotations that have been created for the text. */
    List<ProcessingStep> steps // = []

    private final ObjectMapper mapper; // = new ObjectMapper();

    @JsonProperty("@context")
    Object context

    private static final String REMOTE_CONTEXT = "http://vocab.lappsgrid.org/context-1.0.0.jsonld"

    private static final Map LOCAL_CONTEXT = [
        '@vocab':'http://vocab.lappsgrid.org/',
        'meta':'http://vocab.lappsgrid.org/metadata/',
        'lif':'http://vocab.lappsgrid.org/interchange/',
        'types':'http://vocab.lappsgrid.org/types/',
        'metadata': 'meta:metadata',
        'contains': 'meta:contains',
        'producer': 'meta:producer',
        'url': ['@id':'meta:url', '@type':'@id'],
        'type':['@id':'meta:type', '@type':'@id'],
        'version':'meta:version',
        'text':'lif:text',
        'steps': 'lif:steps',
        'annotations': 'lif:annoations',
        'tokenization': 'types:tokenization/',
        'tagset': 'types:posType/',
        'ner': 'types:ner/',
        'coref': "types:coref/",
        'chunk': "types:chunk/",
        'lookup': "types:lookup/"

    ]

    /** Default (empty) constructor. Does nothing. */
    public Container() {
        this(true)
    }

    public Container(boolean local) {
        content = new Content()
        mapper = new ObjectMapper()
        metadata = new HashMap<String,Object>();
        steps = new ArrayList<ProcessingStep>()
//        context = "http://vocab.lappsgrid.org/context-1.0.0.jsonld"
        if (local) {
            context = LOCAL_CONTEXT
        }
        else {
            context = REMOTE_CONTEXT
        }
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /** Constructs a Container object from the values stored in the Map. */
    public Container(boolean local, Map map) {
        this(local)
        initFromMap(map)
    }

    /** Constructs a Container object from the JSON representation. */
    public Container(String json) {
        this(false)
        Container proxy = mapper.readValue(json, Container.class)
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

    void addStep(ProcessingStep step) {
        this.steps << step
    }

    ProcessingStep getStep(int index) {
        if (index >= 0 && index < steps.si()) {
        return steps[index]
        }
        return null
    }


//    private Content getContent() { return null }
//    private void setContent(Content ignored) { }

    void define(String name, String iri) throws LappsIOException
    {
        if (!(this.context instanceof Map)) {
            throw new LappsIOException("Can not define a context field when a remote context is used.")
        }
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
