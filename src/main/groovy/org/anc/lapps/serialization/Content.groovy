package org.anc.lapps.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * The Content object is a JSON "value object", that is an object with a @value
 * field.
 * <p>
 * Content objects are used to hold the document text and language field.
 * @author Keith Suderman
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content {
    @JsonProperty('@value')
    String value
    @JsonProperty('@language')
    String language

    public Content() { }
}
