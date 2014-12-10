package org.lappsgrid.serialization

import org.lappsgrid.serialization.lif.Annotation
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.lif.View

/**
 * @author Keith Suderman
 */
class ContainerFactory {
    public static Container create() {
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
        return container
    }
}
