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
        T result = null
        try {
            result = (T) mapper.readValue(json, theClass)
        }
        catch(Exception e)
        {
            e.printStackTrace()
            // Ignored. We return null to indicate an error.
        }
        return result;
    }

    public static String toJson(Object object)
    {
        try {
            return mapper.writeValueAsString(object)
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String toPrettyJson(Object object)
    {
        try {
            return prettyPrinter.writeValueAsString(object)
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
