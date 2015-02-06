package org.lappsgrid.serialization

import org.junit.*
import org.lappsgrid.serialization.lif.Contains

import static org.junit.Assert.*

import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.lif.View

/**
 * @author Keith Suderman
 */
class QueryTest {
    @Test
    void  testFindGreeting() {
        Container container = ContainerFactory.createContainer()
        List<View> views = container.findViewsThatContain('Greeting')
        assertTrue views.size() == 2

        views = container.findViewsThatContainBy('Greeting', 'http://www.anc.org')
        assertTrue views.size() == 1

        views = container.findViewsThatContainBy('Greeting', 'http://gate.ac.uk')
        assertTrue views.size() == 1
    }

//    boolean contains(View view, String type, String producer=null) {
//        Map map = view.metadata['contains']
//        if (!map) return false
//        Contains contains = map[type]
//        if (!contains) return false
//        if (producer) {
//            return contains.producer == producer
//        }
//        return true
//    }
}
