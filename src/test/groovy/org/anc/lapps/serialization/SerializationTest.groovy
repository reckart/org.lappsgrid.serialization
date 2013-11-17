package org.anc.lapps.serialization

import org.junit.*
import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class SerializationTest {

    @Test
    public void testToJson() {
        println "SerializationTest.testToJson"
        String json = Serializer.toPrettyJson(getContainer())
        assertTrue(json != null)
    }

    @Test
    public void testRoundTrip() {
        println "SerializationTest.testRoundTrip"
        Container c1 = getContainer()
        String json = Serializer.toJSon(c1)
        Container c2 = Serializer.toContainer(json)
        String json2 = Serializer.toJSon(c2)
        assertTrue('Text is not the same.', c1.text == c2.text)
        assertTrue('Number of features is different.', c1.steps.size() == c2.steps.size())
        assertTrue('Serializations do not match', json == json2)
    }

    Container getContainer() {
        Container c = new Container()
        c.text = "Hello world. Goodbye cruel world."
        ProcessingStep pass = new ProcessingStep()
        pass.metadata['pass'] = '1'
//        pass.annotations << new Annotation(id:'s2', label:'s', start:13, end:33)
        pass.annotations << new Annotation(id:'s1', label:'s', start:0, end:12)
        c.steps << pass

        pass = new ProcessingStep()
        pass.metadata.pass = '2'
        pass.annotations << new Annotation(id:'a1', label:'doc', start:0, end:33)
        c.steps << pass

        return c
    }
}
