package org.lappsgrid.serialization

import org.lappsgrid.serialization.lif.Annotation
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.lif.View
import static org.lappsgrid.discriminator.Discriminators.Uri

/**
 * @author Keith Suderman
 */
class ExampleGenerator {

    // Used to generate token id values.
    int tokenCounter = 0

    void run() {
        Container container = new Container()
        container.language = 'en'
        container.text = 'Hello world'
        println Serializer.toPrettyJson(container)
    }

    void example2() {
        Container container = new Container()
        container.language = 'en'
        //                012345678901
        container.text = 'Hello world.'
        View tokens = new View()
        tokens.addContains('Token', this.class.name, Uri.TOKEN)
        tokens.annotations << token(0, 5, [pos:'UH',lemma:'hello',string:'hello'])
        tokens.annotations << token(6, 11, [pos:'NN',lemma:'world',string:'world'])
        tokens.annotations << token(11, 12, [pos:'PUNCT',string:'.'])

        View sentences = new View()
        sentences.addContains('Sentence', this.class.name, Uri.SENTENCE)
        Annotation sentence = new Annotation(id:'s1', start:0, end:12, label:'Sentence')
        sentences.add(sentence)

        container.addView(tokens)
        container.addView(sentences)

        println Serializer.toPrettyJson(container)
    }

    void example3() {
        Container container = new Container()
        container.language = 'en'
        //                012345678901
        container.text = 'Hello world.'
        View tokens = new View()
        tokens.metadata.annotations = [:]
        tokens.metadata.annotations[Uri.TOKEN] = [producer:this.class.name]
        tokens.annotations << token(0, 5, [pos:'UH',lemma:'hello',string:'hello'])
        tokens.annotations << token(6, 11, [pos:'NN',lemma:'world',string:'world'])
        tokens.annotations << token(11, 12, [pos:'PUNCT',string:'.'])

        View sentences = new View()
        sentences.metadata.annotations = [:]
        sentences.metadata.annotations[Uri.SENTENCE] = [producer: this.class.name]
        Annotation sentence = new Annotation(id:'s1', start:0, end:12, label:Uri.SENTENCE)
        sentences.add(sentence)

        container.addView(tokens)
        container.addView(sentences)


        println Serializer.toPrettyJson(container)
    }

    Annotation token(long start, long end, Map features) {
        Annotation a = new Annotation()
        ++tokenCounter
        a.id = "t${tokenCounter}"
        a.start = start
        a.end = end
        a.label = Uri.TOKEN
        a.features = features
        return a
    }

    public static void main(args) {
        new ExampleGenerator().example3()
    }
}
