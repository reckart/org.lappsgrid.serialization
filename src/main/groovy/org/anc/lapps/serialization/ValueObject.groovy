package org.anc.lapps.serialization

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A JSON-LD <a href="http://www.w3.org/TR/json-ld/#dfn-value-object">value object</a>.
 * <p>
 * While a value object may contains other keys, for our purposes we only need
 * the <i>value</i> and <i>type</i> keys.
 * <p>
 * This class isn't currently used anywhere anymore.
 * @author Keith Suderman
 */
class ValueObject {
    @JsonProperty('@value')
    String value
    @JsonProperty('@type')
    String type

    public ValueObject() { }

    public ValueObject(String type, String value)
    {
        this.type = type;
        this.value = value;
    }
}
