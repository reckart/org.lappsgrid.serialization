package org.anc.lapps.serialization

import com.github.jsonldjava.core.JsonLdApi
import com.github.jsonldjava.core.JsonLdOptions
import com.github.jsonldjava.core.JsonLdProcessor
import com.github.jsonldjava.utils.JSONUtils
import groovy.json.JsonBuilder

/**
 * Created by Chunqi SHI (shicq@brandeis.edu) on 2/7/14.
 */

//
// http://www.w3.org/TR/json-ld-api/#jsonldoptions
//
class JsonLd {
    static String text = JsonLd.class.getResource( '/JsonLd.config' ).text
    static config = new ConfigSlurper().parse(text)

    // Define all JSON-LD keywords
    static def all_jsonld_keywords = [
        "@context", // L 0
        "@id", // L 0
        "@language", // L 1 @context
        "@type", // L 0
        "@container", // L1 @context
        "@list", // L1 @context
        "@set", // L 1 @context
        "@reverse", // L 1 @context
        "@index", // L 0
        "@base", // L 1 @context
        "@vocab", // L 1 @context
        "@graph"  // L 0
    ] as String []

    // Define the JSON-LD keywords, which can be used in 0 level.
    static def level0_jsonld_keywords = [
            "@context", // L 0
            "@id", // L 0
            "@type", // L 0
            "@index", // L 0
            "@graph"  // L 0
    ] as String []

    static def ignoredProperties = [
        "class"
    ]

    // Only level 0 keywords are considered.
    static def configKeywords = []

    static {
        level0_jsonld_keywords.eachWithIndex { String entry, int i ->
            configKeywords.add(entry.replace("@", "jsonld_"))
        }
        println configKeywords
    }

    def content
    def jsonld

    public JsonLd(content = null) {
        this.content = content

//        def jsonapi = new JsonLdApi(Object input, Object context, JsonLdOptions opts)

//        JsonLdOptions options = new JsonLdOptions();
//        options.setBase()
//        Object compact = JsonLdProcessor.compact(jsonObject, context, options);


        def clsname = getClassLastName(content)
        def root =  "class_" + clsname
        if (is_null(clsname)) {
//            jsonld =
        } else if (is_str(clsname)) {
            jsonld = str2jsonld(root, content)
        } else if (is_num(clsname)) {
            jsonld = num2jsonld(root, content)
        } else if (is_bool(clsname)) {
            jsonld = bool2jsonld(root, content)
        } else if (is_map(clsname)) {
            jsonld = map2jsonld(root, content)
        } else if (is_list(clsname)) {
            jsonld = list2jsonld(root, content)
        } else {
            jsonld = obj2jsonld(root, content)
        }
    }

    public String toString(){
        return jsonld
    }

    def getClassLastName(obj){
        if (obj == null)
            return "null"
        return obj.class.toString().split("\\.")[-1].toLowerCase()
    }

    def bool2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        jsonobj.put("@type", "xsd:boolean")
        return jsonobj
    }

    def int2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        jsonobj.put("@type", "xsd:integer")
        return jsonobj
    }

    def float2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        jsonobj.put("@type", "xsd:double")
        return jsonobj
    }

    def str2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        return jsonobj
    }

    def list2jsonld(root, list) {
        def json = new JsonBuilder(list).toString()
        def jsonobj = JSONUtils.fromString(json)
        return jsonobj
    }

    def map2jsonld(root, Map map) {
        def json = new JsonBuilder(map).toString()
        def jsonobj = JSONUtils.fromString(json)
        map.eachWithIndex { Map.Entry<Object, Object> entry, int i ->
            if (is_null(entry.value)) {
                // TODO: what to do with null in map?
            } else if (is_bool(entry.value)) {
                jsonobj.put(entry.key, bool2jsonld(entry.value))
            } else if (is_str(entry.value)) {
                jsonobj.put(entry.key, str2jsonld(entry.value))
            } else if (is_int(entry.value)) {
                jsonobj.put(entry.key, int2jsonld(entry.value))
            } else if (is_float(entry.value)) {
                jsonobj.put(entry.key, float2jsonld(entry.value))
            } else if (is_map(entry.value)) {
                jsonobj.put(entry.key, map2jsonld(entry.value))
            } else if (is_list(entry.value)) {
                jsonobj.put(entry.key, list2jsonld(entry.value))
            } else {
                jsonobj.put(entry.key, obj2jsonld(entry.value))
            }
        }
        return jsonobj
    }

    /**
     * from configure file reading the string using extend path.
     * @param keyEx: extended key path.
     * @return
     */
    def getJsonObj(String keyEx) {
        String sval = get(keyEx)
        if (sval == null)
            return null
        else
            sval = sval.trim()
        if (sval.charAt(0) != '{') {
            sval = "{" + sval + "}" // only validated json can be parsed into JSON object.
        }
        return JSONUtils.fromString(sval)
    }

    def obj2jsonld(root, obj){
        String json = new JsonBuilder(obj).toString()
        println "-------json------"
        println json
        def jsonobj = JSONUtils.fromString(json)

        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
                // update jsonobj
                jsonobj.putAll(jsonconfig)
        }

        def jsonid = getJsonObj(root + ".jsonld_id")
        println "------id----------"
        println jsonid
        if (jsonid != null)
            // update jsonobj
            jsonobj.putAll(jsonid)

        def jsoncontext = getJsonObj(root + ".jsonld_context")
//        def base = get(root + ".jsonld_base")

        println "------context----------"
        println jsoncontext

        JsonLdOptions opts = new JsonLdOptions()
        println "-------jsonobj------"
        println jsonobj

        Object compact = JsonLdProcessor.compact(jsonobj, jsoncontext, opts);
        println "--------------compact-------------"
        println compact
        return JSONUtils.toPrettyString(compact)
//
//
//        configKeywords.eachWithIndex { String keywordEntry, int j ->
//            def configPath = root + "." + keywordEntry
//            println configPath
//            println get(configPath)
//        }
//
//        obj.getProperties().eachWithIndex { Map.Entry<Object, Object> entry, int i ->
//            if (! ignoredProperties.contains(entry.key)) {
//                root =  "class_" + clsname + "." + entry.key
//                configKeywords.eachWithIndex { String keywordEntry, int j ->
//                    def configPath = root + "." + keywordEntry
////                    println configPath
////                    println get(configPath)
//                }
//                println entry.key
//                println getClassLastName(entry.value)
//                println entry.value.getProperties()
//            }
//
//        }


    }

    def is_str(clsname){
        return "string" in clsname
    }

    def is_map(clsname) {
        return "map" in clsname
    }

    def is_list(clsname) {
        return "list" in clsname || "array" in clsname
    }

    def is_null(clsname) {
        return "null" in clsname
    }

    def is_bool(clsname) {
        return "bool" in clsname
    }

    def is_int(clsname) {
        return "integer" in clsname || "long" in clsname
    }

    def is_float(clsname) {
        return "float" in clsname || "double" in clsname
    }

    Object get(String keyEx) {
        def target = config
        keyEx.split("\\.").eachWithIndex {
            key, i ->
                if (target != null)
                    target = target.get(key)
        }
        return target
    }
}

