package org.lappsgrid.serialization

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

/**
 * @author Keith Suderman
 */
class ContainerTest {

    private static final String INPUT_FILE_NAME = "/Bartok.txt"

    private File TEST_FILE

    @Before
    public void setup()
    {
        TEST_FILE = File.createTempFile("containerTest", ".json")
    }

    @After
    public void tearDown()
    {
        if (!TEST_FILE.delete())
        {
            TEST_FILE.deleteOnExit()
        }
        TEST_FILE = null
    }

    @Ignore
    public void testTempFile()
    {
        println "Temp file is ${TEST_FILE.path}"
        assertTrue("Temp file not found", TEST_FILE.exists())
    }

    @Test
    public void testPrettyJson()
    {
        println "ContainerTest.testPrettyJson"
        Container original = new Container();
        original.text = getResource(INPUT_FILE_NAME)
//        TEST_FILE.text = original.toPrettyJson()
//        FileUtils.write(TEST_FILE, original.toPrettyJson());
        TEST_FILE.withWriter('UTF-8') {
            it.write(original.toPrettyJson())
            it.flush()
            it.close()
        }


//        final String json = FileUtils.read(TEST_FILE);
        final String json = TEST_FILE.getText('UTF-8')
        //println json
        Container copy = new Container(json);
        //println copy.toPrettyJson()
        assertTrue(original.text == copy.text)
    }

    @Test
    public void testJson()
    {
        println "ContainerTest.testJson"
        final Container original = new Container()
        original.text = getResource(INPUT_FILE_NAME)
//        TEST_FILE.text = original.toJson()
        TEST_FILE.withWriter('UTF-8') {
            it.write(original.toJson())
            it.flush()
            it.close()
        }
        final String json = TEST_FILE.getText('UTF-8')
        //println json
        Container copy = new Container(json)
        //println copy.toPrettyJson()
//        println original.text
//        println copy.text
        assertTrue(original.text == copy.text)
    }

    @Test
    public void testContainerMetadata()
    {
        println "ContainerTest.testContainerMetadata"
        Container container = new Container()
        container.text = 'Hello world'
        container.language = 'en'
        container.metadata.text = 'text'
        container.metadata.map = [foo:'foo', bar:'bar']
        container.metadata.list = [0,1,2,3,4]

        String json = container.toJson()
        container = new Container(json)
        assertTrue container.text == 'Hello world'
        assertNotNull container.metadata
        assertNotNull container.metadata.map
        assertTrue container.metadata.map.foo == 'foo'
        assertTrue container.metadata.map.bar == 'bar'
        assertNotNull container.metadata.list
        assertTrue container.metadata.list instanceof List
        assertTrue container.metadata.list.size() == 5
        (0..4).each { i ->
            assertTrue container.metadata.list[i] == i
        }
        println container.toPrettyJson()
    }

    @Test
    public void testAnnotations() {
        println "ContainerTest.testAnnotations"
        Container container = new Container()
        container.text = 'hello world'
        container.metadata = [test: 'this is a test']
        View view = new View()
        view.metadata.producedBy = 'Test code'
        def a = new Annotation()
        a.id = 'a12'
        a.start = 0
        a.end = 5
        a.label = 'Token'
        a.atType = 'Token'
        a.type = 'Lapps:TextAnnotation'
        a.features.pos = 'NN'
        a.features.lemma = 'hello'
        view.annotations.add a
        container.views.add view

        String json = container.toPrettyJson()

        container = new Container(json)
        assertTrue(container.views.size() == 1)
        view = container.views[0]
        assertTrue(view.annotations.size() == 1)
        a = view.annotations[0]
        assertTrue(a.label == 'Token')
        assertTrue(a.type == 'Lapps:TextAnnotation')
//        assertTrue(a.type == 'Token')
        println json
    }

    @Ignore
    public void testContext() {
        println "ContainerTest.testContext"
        String uri = 'http://langrid.org/vocab/morpheme'
        Container container = new Container()
        Map context = container.context
        context['morpheme'] = [
                '@id': uri,
                '@type': '@id'
        ]
        container.text = 'hello world'
        def view = new View()
        def a = new Annotation()
        a.label = 'morpheme'
        a.id = "m1"
        a.start = 0
        a.end = 5
        String json =  container.toPrettyJson();
        container = new Container(json)
        assertTrue(container.context.toString(), container.context.morpheme != null)
        assertTrue(container.context.morpheme['@id'] == uri)
        assertTrue(container.context.morpheme['@type'] == '@id')
        println json
    }

//    @Test
//    public void testLocalContext() {
//        Container container = new Container(true)
//        assertTrue("Context is not a map!", container.context instanceof Map)
//        // Now check a few a few values in the context.
//        assertTrue('http://vocab.lappsgrid.org/' == container.context['@vocab'])
//        assertTrue('http://vocab.lappsgrid.org/types/' == container.context.types)
//    }

    @Test
    public void testDefaultContext() {
        Container container = new Container()
        assertTrue container.context == Container.REMOTE_CONTEXT
        container = new Container(container.toJson())
        assertTrue container.context == Container.REMOTE_CONTEXT

        container = new Container(Container.ContextType.REMOTE)
        assertTrue container.context == Container.REMOTE_CONTEXT

        container = new Container(Container.ContextType.LOCAL)
        assertTrue container.context == Container.LOCAL_CONTEXT
    }

    @Test
    public void testRemoteContext() {
        Container container = new Container(false)
        assertTrue("Context is not a string!", container.context instanceof String)
        assertTrue(container.context == "http://vocab.lappsgrid.org/context-1.0.0.jsonld")
        // Make sure the URL can be dereferenced.
        URL url = new URL(container.context)
        assertNotNull(url.text)
    }

    private String getResource(String name) {
        return this.class.getResource(name).getText('UTF-8')
    }
}
