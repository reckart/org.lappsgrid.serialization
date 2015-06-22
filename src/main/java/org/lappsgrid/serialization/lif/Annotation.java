/*
 * Copyright 2014 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.lappsgrid.serialization.lif;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Information about a single standoff annotation.
 *
 * @author Keith Suderman
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({"id", "start", "end", "@type", "type", "label", "features", "metadata"})
public class Annotation {
    /** A unique ID assigned to this annotation.
     * <p>
     * <em>Note:</em> This ID value is assigned to the annotation by the framework
     * and is not to be confused with an ID (xml:id, etc) value that the annotation
     * itself might contain.
     */
    protected String id;

    /** The label used for the annotation, e.g. tok, s, etc. */
//    @JsonProperty('@type')
    protected String label;

    /** The {@literal @}type value (if any) for the JSON element. */
    @JsonProperty("@type")
    protected String atType;

    /** A type from the Lappsgrid vocabulary. */
    protected String type;

    /** The start offset of the annotation. */
    protected Long start = null;

    /** The end offset of the annotation. */
    protected Long end = null;

    /** Features of the annotation. Featues are assumed to be String name/value pairs. */
	 protected Map<String,Object> features = new HashMap<>();

    /** Features assigned by the framework to the annotation. E.g. a confidence
     * score, the processor that generated the annotation etc.
     */
	 protected Map<String,Object> metadata = new HashMap<>();

    public Annotation() { }

    public Annotation(String id, String label, long start, long end) {
        this.id = id;
        this.label = label;
        this.start = start;
        this.end = end;
    }

    public Annotation(String label, long start, long end) {
        this.label = label;
        this.start = start;
        this.end = end;
    }

    public Annotation(Map map) {
        Set<Map.Entry> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            switch (entry.getKey().toString())
            {
                case "label":
                    this.label = entry.getValue().toString();
                    break;
                case "@type":
                    this.atType = entry.getValue().toString();
                    break;
                case "type":
                    this.type = entry.getValue().toString();
                    break;
					case "start":
						this.start = (Long) entry.getValue();
						break;
					case "end":
						this.end = (Long) entry.getValue();
						break;
					case "id":
						this.id = entry.getValue().toString();
						break;
					case "features":
						this.features = (Map<String,Object>) entry.getValue();
						break;
					case "metadata":
						this.metadata = (Map<String,Object>) entry.getValue();
						break;
            }
        }
    }

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getAtType()
	{
		return atType;
	}

	public void setAtType(String atType)
	{
		this.atType = atType;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Long getStart()
	{
		return start;
	}

	public void setStart(Long start)
	{
		this.start = start;
	}

	public Long getEnd()
	{
		return end;
	}

	public void setEnd(Long end)
	{
		this.end = end;
	}

	public Map<String, Object> getFeatures()
	{
		return features;
	}

	public void setFeatures(Map<String, Object> features)
	{
		this.features = features;
	}

	public Map<String, Object> getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata)
	{
		this.metadata = metadata;
	}

	@JsonIgnore
    public void addFeature(String name, String value) {
        features.put(name, value);
    }

    @JsonIgnore
    public String getFeature(String name) {
        return features.get(name).toString();
    }

    public String toString() {
		 return String.format("%s (%d-%d) %s", label, start, end, features.toString());
    }

}
