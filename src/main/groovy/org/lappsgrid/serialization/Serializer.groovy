package org.lappsgrid.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

/**
 * @author Keith Suderman
 */
class Serializer {
    private static ObjectMapper mapper;
    private static ObjectMapper prettyPrinter;

    static {
        mapper = new ObjectMapper()
        mapper.disable(SerializationFeature.INDENT_OUTPUT)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        prettyPrinter = new ObjectMapper()
        prettyPrinter.enable(SerializationFeature.INDENT_OUTPUT)
        prettyPrinter.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
    private Serializer() {}

    public static <T> T parse(String json, Class<T> theClass) {
        return (T) mapper.readValue(json, theClass)
    }

    public static String toJson(Object object)
    {
        mapper.writeValueAsString(object)
    }

    public static String toPrettyJson(Object object)
    {
        prettyPrinter.writeValueAsString(object)
    }
}
