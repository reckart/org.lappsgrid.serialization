package org.lappsgrid.serialization

import org.lappsgrid.serialization.lif.Annotation
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.lif.View

/**
 * This factory generates Container objects used in tests.
 *
 * @author Keith Suderman
 */
class ContainerFactory {
    public static Container createContainer() {
        Container container = new Container()
        container.language
        container.content.value = 'hello world'
        container.content.language = 'en'
        container.metadata = [test: 'this is a test']
        View view = container.newView()
        view.addContains('Token', 'http://www.anc.org', 'Span')
        def a = view.newAnnotation('tok1', 'Token', 0, 5)
        a.features.pos = 'UH'
        a.features.lemma = 'hello'

        a = view.newAnnotation('tok2', 'Token', 6, 11)
        a.features.pos = 'NN'
        a.features.lemma = 'world'

        view = container.newView()
        view.addContains('Location', 'http://www.anc.org', 'Annotation')
        a = view.newAnnotation('loc1', 'Location')
        a.features['target'] = 'tok2'

        view = container.newView()
        view.addContains('Greeting', 'http://www.anc.org', 'Annotation')
        a = view.newAnnotation('g1', 'Greeting')
        a.features['target'] = ['tok1', 'tok2']

        view = container.newView()
        view.addContains('Greeting', 'http://gate.ac.uk', 'Annotation')
        a = view.newAnnotation('g2', 'Greeting')
        a.features['target'] = 'tok1'
        return container
    }
}
