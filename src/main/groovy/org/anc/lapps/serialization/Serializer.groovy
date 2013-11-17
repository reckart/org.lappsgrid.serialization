package org.anc.lapps.serialization

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

/**
 * The Serializer class can be used to serialize {@link Container} objects into
 * their JSON representation, and the JSON representation back into Container
 * objects.
 *
 * @author Keith Suderman
 */
public class Serializer {

    /**
     * Serializes a {@link Container} object into its JSON representation.
     *
     * @param container The container object to be serialized.
     * @return the JSON representation of the Container object.
     */
    static public String toJSon(Container container) {
        return new JsonBuilder(container).toString()
    }

    /**
     * Serializes a {@link Container} object into its JSON representation. The
     * resulting string is formatted (pretty-printed) to be more human
     * readable.
     *
     * @param container The container object to be serialized.
     * @return the JSON representation of the Container object.
     */
    static public String toPrettyJson(Container container) {
        return new JsonBuilder(container).toPrettyString()
    }

    /**
     * Serializes the JSON representation into a new {@link Container} object.
     *
     * @param json The JSON to be serialized into the container.
     * @return a new Container object
     */
    static public Container toContainer(String json) {
        def map = new JsonSlurper().parseText(json)
        println map
        return new Container(map)
    }
}
