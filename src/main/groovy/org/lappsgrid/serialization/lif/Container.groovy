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
package org.lappsgrid.serialization.lif

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.lappsgrid.serialization.LappsIOException

/**
 * Container objects are the out wrapper for all LIF objects.
 *
 * @author Keith Suderman
 */
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder(["context","metadata","text","views"])
public class Container {

    public enum ContextType {
        LOCAL, REMOTE
    }

    /** The text that is to be annotated. */
    @JsonProperty('text')
    Content content;

    /** Any meta-data attached to this container. */
    Map metadata

    /** The list of annotations that have been created for the text. */
    List<View> views

    @JsonProperty("@context")
    Object context

    public static final String REMOTE_CONTEXT = "http://vocab.lappsgrid.org/context-1.0.0.jsonld"

    //TODO Keeping the local context up to date is a never ending process.
    public static final Map LOCAL_CONTEXT = [
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
        'views': 'lif:views',
        'annotations': 'lif:annotations',
        'tokenization': 'types:tokenization/',
        'tagset': 'types:posType/',
        'ner': 'types:ner/',
        'coref': "types:coref/",
        'chunk': "types:chunk/",
        'lookup': "types:lookup/",
        'token': "http://vocab.lappsgrid.org/Token#",
        "common": "http://vocab.lappsgrid.org/Annotation#",
        "id":"common:id",
        "start":"common:start",
        "label":"common:label",
        "end":"common:end",
        "pos":"token:pos",
        "lemma":"token:lemma",
        "kind":"token:kind",
        "length":"token:length",
        "orth":"token:orth",
    ]

    /** Default (empty) constructor uses the remote context. */
    public Container() {
        this(ContextType.REMOTE)
    }

    protected Container(ContextType type) {
        content = new Content()
//        mapper = new ObjectMapper()
        metadata = new HashMap<String,Object>();
        views = new ArrayList<View>()
        if (type == ContextType.LOCAL) {
            context = LOCAL_CONTEXT
        }
        else {
            context = REMOTE_CONTEXT
        }
    }

    public Container(Map map) {
        initFromMap(map)
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

    View newView() {
        View view = new View();
        views.add(view)
        return view
    }

    void addView(View view) {
        this.views << view
    }

    View getView(int index) {
        if (index >= 0 && index < views.size()) {
            return views[index]
        }
        return null
    }

    List<View> findViewsThatContain(String type) {
        views.findAll { it?.metadata?.contains[type] }
    }

    List<View> findViewsThatContainBy(String type, String producer) {
        views.findAll { it?.metadata?.contains[type]?.producer == producer }
    }

    void setMetadata(String name, Object value) {
        this.metadata[name] = value
    }

    Object getMetadata(String name) {
        return this.metadata[name]
    }

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

    private void initFromMap(Map map) {
        if (map == null) {
            return
        }
        this.context = map.context
        this.content = new Content()//value:map.text.value, language:map.text.language)
        this.text = map.text['@value']
        this.language = map.text['@language']
        this.metadata = [:]
        map.metadata.each { name, value ->
            this.metadata[name] = value
        }
        this.views = []
        map.views.each { v ->
            View view = new View(v)
//            v.metadata.each { key,value ->
//                view.metadata[key] = value
//            }
//            v.annotations.each { annotation ->
//                view.annotations << new Annotation(annotation)
//            }
            this.views << view
        }
    }

}
