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

import java.util.*;

/**
 * Holds information for the 'contains' sections of a {@link View}'s
 * metadata section.
 * <p/>
 * The <em>contains</em> metadata allows pipelines (planners or composers) to determine the
 * contents of a ProcessingStep without having to traverse the contents of the
 * <em>annotations</em> list.
 *
 * @author Keith Suderman
 */
public class Contains {
    /**
     * The URL of the processor that produced the annotations.
     */
    protected String url;

    /**
     * The name of the processors that produced the annotations.  For Java
     * processors this will be the fully qualified class name of the processor
     * including version information.
     */
    protected String producer;

    /**
     * The annotation type.
     */
    protected String type;

    public Contains() { }

    public Contains(String producer, String type)
    {
        this(producer, type, null);
    }

    public Contains(String producer, String type, String url)
    {
        this.producer = producer;
        this.type = type;
        this.url = url;
    }

    public Contains(Map map) {
        if (map == null) {
            return;
        }
        this.url = map.get("url").toString();
        this.producer = map.get("producer").toString();
        this.type = map.get("type").toString();
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getProducer()
    {
        return producer;
    }

    public void setProducer(String producer)
    {
        this.producer = producer;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
