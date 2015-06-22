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

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.lappsgrid.serialization.lif.Annotation
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.lif.Contains
import org.lappsgrid.serialization.lif.View

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

/**
 * @author Keith Suderman
 */
@Ignore
class MetadataTest {

    String json
    int id

    @Before
    void setup() {

        json = this.class.getResource('/metadata.json').text
        id = 0
    }

    @After
    void tearDown() {
        json = null
    }

    @Test
    void parseMetadata() {
        Container c = Serializer.parse(json, Container) // new Container(json)
        assertNotNull "Unable to parse json", c
        assertTrue(c.text == 'Fido barks.')
        assertTrue(c.language == 'en')
        assertTrue(c.views.size() == 1)
        assertTrue(c.views[0].annotations.size() == 3)
        assertTrue(c.views[0].metadata.contains.size() == 2)
        def info = c.views[0].metadata.contains
        assertNotNull(info.tokens)
        assertNotNull(info.pos)
        println Serializer.toPrettyJson(c) // c.toPrettyJson()
    }

    @Test
    void addMetadataTest() {
        Container c1 = new Container()
        c1.setMetadata("test", "value")
        String json = Serializer.toJson(c1)
        Container c2 = Serializer.parse(json, Container) // new Container(c1.toJson())
        assertTrue("value" == c2.getMetadata("test"))
        println Serializer.toPrettyJson(c2) // c2.toPrettyJson()
    }

    @Ignore
    void generateMetadata() {
        Container c = new Container(false)
        c.metadata.version = '1.0'
        c.text = 'Fido barks.'
        c.language = 'en'
        def contains = [:]
        def tokens = new Contains()
        tokens.with {
            url = 'http://grid.anc.org:8080/service_manager/invoker/anc:gate.tokenizer_1.3.4'
            producer = 'org.anc.lapps.gate.tokenizer'
            type = "tokenization:gate"
        }
        def pos = new Contains()
        pos.with {
            url = 'http://grid.anc.org:8080/service_manager/invoker/anc:gate.tagger_1.3.4'
            producer = 'org.anc.lapps.gate.Tagger'
            type = "tagset:penn"
        }
//        def sentences = new Contains()
//        sentences.with {
//            url = 'http://grid.and.org:8080/service_manager/invoker/anc:stanford.splitter_1.4.0'
//            producer = 'org.anc.lapps.stanford.SentenceSplitter'
//            type = "sentences:stanford"
//        }
        contains.Token = tokens
        contains.pos = pos
//        contains.Sentence = sentences

        View view = new View()
        view.metadata.contains = contains
        view.with {
            add makeAnnotation('Token', 0, 4, [pos:'NN',lemma:'Fido'])
            add makeAnnotation('Token', 5, 10, [pos:'VBZ', lemma:'bark'])
            add makeAnnotation('Token', 11, 12, [pos:'.'])
        }
        c.views.add(view)
        //println c.context
        println Serializer.toPrettyJson(c)
    }

    @Ignore
    void testContains() {
        Container c = new Container()
        c.metadata.version = '1.0'
        c.text = 'Fido barks.'
        c.language = 'en'
        def contains = [:]
        def tokens = new Contains()
        tokens.with {
            url = 'http://grid.anc.org:8080/service_manager/invoker/anc:gate.tokenizer_1.3.4'
            producer = 'org.anc.lapps.gate.tokenizer'
            type = 'tokenization:gate'
        }
        def pos = new Contains()
        pos.with {
            url = 'http://grid.anc.org:8080/service_manager/invoker/anc:gate.tagger_1.3.4'
            producer = 'org.anc.lapps.gate.Tagger'
            type = 'tagset:penn'
        }
        contains.Token = tokens
        contains.pos = pos

        View view = new View()
        view.metadata.contains = contains
        c.views.add(view)
        //println c.context
        println Serializer.toPrettyJson(c)
    }

    Annotation makeAnnotation(String name, int start, int end, Map features) {
        Annotation a = new Annotation()
        a.id = "t${++id}"
        a.label = name
        a.start = start
        a.end = end
        a.features = features
        //println "Created annotation ${a}"
        return a
    }
}
