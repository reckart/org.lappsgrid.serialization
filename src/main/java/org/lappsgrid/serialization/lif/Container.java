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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.lappsgrid.serialization.LappsIOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container objects are the outer wrapper for all LIF objects.
 * <p/>
 * A Lappsgrid Container consists of:
 * <ol>
 * <li>the text content (with possibly the language).</li>
 * <li>some metadata</li>
 * <li>a list of {@link View}s</li>
 * </ol>
 * The Container object also contains helper methods to find the {@link View}s
 * containing a given annotation type:
 * <p/>
 * For example:
 * <pre>
 *     <code>
 *         String token = "http://vocab.lappsgrid.org/Token"
 *         Container container = new Container();
 *         container.setText("Goodbye cruel world, I am leaving you today.");
 *         container.setLanguage("en");
 *         View view = container.newView();
 *         view.addAnnotation("tok1", token, 0, 7);
 *         ...
 *         List<View> views = container.findViewsThatContain(token);
 *         assertEquals(view, views.get(0));
 *     </code>
 * </pre>
 * <p/>
 * Since LIF objects are serialized to JSON-LD they also define a {@code context}. By
 * default all LIF objects refer to the remote context document
 * <a href="http://vocab.lappsgrid.org/context-1.0.0.jsonld">http://vocab.lappsgrid.org/context-1.0.0.jsonld</a>.
 * However, a local (in-line) context can be used with allows the context to be
 * manipulated at runtime.
 * <pre>
 *     <code>
 *         Container container = new Container(ContextType.LOCAL);
 *         Map<String,String> context = (Map) container.getContext();
 *         context.put("@vocab", "http://example.com/custom/vocabulary");
 *     </code>
 * </pre>
 *
 * @author Keith Suderman
 */
@JsonPropertyOrder({"context", "metadata", "text", "views"})
public class Container
{

	public enum ContextType
	{
		LOCAL, REMOTE
	}

	/**
	 * The text that is to be annotated.
	 */
	@JsonProperty("text")
	protected Content content;

	/**
	 * Any meta-data attached to this container.
	 */
	protected Map<String, Object> metadata;

	/**
	 * The list of annotations that have been created for the text.
	 */
	protected List<View> views;

	@JsonProperty("@context")
	protected Object context;

	public static final String REMOTE_CONTEXT = "http://vocab.lappsgrid.org/context-1.0.0.jsonld";

	//TODO Keeping the local context up to date is a never ending process.
//    public static final Map LOCAL_CONTEXT = [
//        '@vocab':'http://vocab.lappsgrid.org/',
//        'meta':'http://vocab.lappsgrid.org/metadata/',
//        'lif':'http://vocab.lappsgrid.org/interchange/',
//        'types':'http://vocab.lappsgrid.org/types/',
//        'metadata': 'meta:metadata',
//        'contains': 'meta:contains',
//        'producer': 'meta:producer',
//        'url': ['@id':'meta:url', '@type':'@id'],
//        'type':['@id':'meta:type', '@type':'@id'],
//        'version':'meta:version',
//        'text':'lif:text',
//        'views': 'lif:views',
//        'annotations': 'lif:annotations',
//        'tokenization': 'types:tokenization/',
//        'tagset': 'types:posType/',
//        'ner': 'types:ner/',
//        'coref': "types:coref/",
//        'chunk': "types:chunk/",
//        'lookup': "types:lookup/",
//        'token': "http://vocab.lappsgrid.org/Token#",
//        "common": "http://vocab.lappsgrid.org/Annotation#",
//        "id":"common:id",
//        "start":"common:start",
//        "label":"common:label",
//        "end":"common:end",
//        "pos":"token:pos",
//        "lemma":"token:lemma",
//        "kind":"token:kind",
//        "length":"token:length",
//        "orth":"token:orth",
//    ]

	/**
	 * Default (empty) constructor uses the remote context.
	 */
	public Container()
	{
		this(ContextType.REMOTE);
	}

	protected Container(ContextType type)
	{
		content = new Content();
//        mapper = new ObjectMapper()
		metadata = new HashMap<>();
		views = new ArrayList<>();
		if (type == ContextType.LOCAL)
		{
			context = new HashMap<String, String>();
		} else
		{
			context = REMOTE_CONTEXT;
		}
	}

	public Container(Map map)
	{
		initFromMap(map);
	}

	@JsonIgnore
	public void setLanguage(String lang)
	{
		content.language = lang;
	}

	@JsonIgnore
	public String getLanguage()
	{
		return content.language;
	}

	@JsonIgnore
	public void setText(String text)
	{
		this.content.value = text;
	}

	@JsonIgnore
	public String getText()
	{
		return this.content.value;
	}

	public View newView()
	{
		View view = new View();
		views.add(view);
		return view;
	}

	public void addView(View view)
	{
		this.views.add(view);
	}

	public View getView(int index)
	{
		if (index >= 0 && index < views.size())
		{
			return views.get(index);
		}
		return null;
	}

	public List<View> getViews()
	{
		return views;
	}

	public void setViews(List<View> views)
	{
		this.views = views;
	}

	public List<View> findViewsThatContain(String type)
	{
		List<View> result = new ArrayList<>();
		for (View view : views)
		{
			Contains c = view.getContains(type);
			if (c != null)
			{
				result.add(view);
			}
		}
		return result;
	}

	public List<View> findViewsThatContainBy(String type, String producer)
	{
		List<View> result = new ArrayList<>();
		for (View view : views)
		{
			Contains c = view.getContains(type);
			if (c != null && producer.equals(c.getProducer()))
			{
				result.add(view);
			}

		}
		return result;
	}

	public void setMetadata(String name, Object value)
	{
		this.metadata.put(name, value);
	}

	public Object getMetadata(String name)
	{
		return this.metadata.get(name);
	}
	public Map<String,Object> getMetadata()
	{
		return this.metadata;
	}
	public void setMetdata(Map<String,Object> map)
	{
		this.metadata = map;
	}

	public void define(String name, String iri) throws LappsIOException
	{
		if (!(this.context instanceof Map))
		{
			throw new LappsIOException("Can not define a context field when a remote context is used.");
		}
		Map<String,Object> context = (Map<String,Object>)this.context;
		if (context.get(name) != null)
		{
			throw new LappsIOException("Context for ${name} already defined.");
		}
		Map<String,String> element = new HashMap<>();
		element.put("@id", iri);
		element.put("@type", "@id");
		context.put(name, element);
	}

	private void initFromMap(Map<String,Object> map)
	{
		if (map == null)
		{
			return;
		}
		this.context = map.get("context");
		this.content = new Content();
		Map<String, String> text = (Map<String,String>)map.get("text");
		if (text != null)
		{
			this.setText((String) text.get("@value"));
			String lang = (String) text.get("@language");
			if (lang != null)
			{
				this.setLanguage(lang);
			}
		}
		this.metadata = (Map) map.get("metadata");
		this.views = new ArrayList<View>();
		List<Map> views = (List<Map>) map.get("views");
		for (Map v : views)
		{
			this.views.add(new View(v));
		}
//		this.text = map.text['@value']
//		this.language = map.text['@language']
//		this.metadata =[:]
//		map.metadata.each {
//		name, value ->
//				  this.metadata[name] = value
//		}
//		this.views =[]
//		map.views.each {
//			v ->
//				  View view = new View(v)
//					this.views << view
//		}
	}

}
