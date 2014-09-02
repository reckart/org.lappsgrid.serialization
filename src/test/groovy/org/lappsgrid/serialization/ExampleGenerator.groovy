package org.lappsgrid.serialization

/**
 * @author Keith Suderman
 */
class ExampleGenerator {

    void run() {
        Container container = new Container()
        container.language = 'en'
        container.text = 'Hello world'
        println container.toPrettyJson()
    }

    public static void main(args) {
        new ExampleGenerator().run()
    }
}
