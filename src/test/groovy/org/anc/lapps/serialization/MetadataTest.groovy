package org.anc.lapps.serialization

import org.anc.resource.ResourceLoader
import org.junit.*
import org.lappsgrid.vocabulary.Contents

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
@Ignore
class MetadataTest {

    String json
    int id

    @Before
    void setup() {
        json = ResourceLoader.loadString('metadata.json')
        id = 0
    }

    @After
    void tearDown() {
        json = null
    }

    @Test
    void parseMetadata() {
        Container c = new Container(json)
        assertTrue(c.text == 'Fido barks.')
        assertTrue(c.language == 'en')
        assertTrue(c.steps.size() == 1)
        assertTrue(c.steps[0].annotations.size() == 3)
        assertTrue(c.steps[0].metadata.contains.size() == 2)
        def info = c.steps[0].metadata.contains
        assertNotNull(info.tokens)
        assertNotNull(info.pos)
        println c.toPrettyJson()
    }

    @Test
    void addMetadataTest() {
        Container c1 = new Container(false)
        c1.setMetadata("test", "value")
        Container c2 = new Container(c1.toJson())
        assertTrue("value" == c2.getMetadata("test"))
        println c2.toPrettyJson()
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
            type = Contents.Tokenizations.ANNIE
        }
        def pos = new Contains()
        pos.with {
            url = 'http://grid.anc.org:8080/service_manager/invoker/anc:gate.tagger_1.3.4'
            producer = 'org.anc.lapps.gate.Tagger'
            type = Contents.TagSets.GATE
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

        ProcessingStep step = new ProcessingStep()
        step.metadata.contains = contains
        step.with {
            add makeAnnotation('Token', 0, 4, [pos:'NN',lemma:'Fido'])
            add makeAnnotation('Token', 5, 10, [pos:'VBZ', lemma:'bark'])
            add makeAnnotation('Token', 11, 12, [pos:'.'])
        }
        c.steps.add(step)
        //println c.context
        println c.toPrettyJson()
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

        ProcessingStep step = new ProcessingStep()
        step.metadata.contains = contains
        c.steps.add(step)
        //println c.context
        println c.toPrettyJson()
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
