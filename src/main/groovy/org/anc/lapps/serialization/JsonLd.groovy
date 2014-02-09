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
    def jsonldobj

    public JsonLd(content = null) {
        this.content = content
        Object[] jsonlds = null
        def clsname = getClassLastName(content)
        def root =  "class_" + clsname
        def jsonconfigobj = [:]
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonconfigobj.putAll(jsonconfig)
        }

        if (is_null(clsname)) {
//            jsonld =
        } else if (is_str(clsname)) {
            jsonlds = str2jsonld(root, content)
        } else if (is_int(clsname)) {
            jsonlds = int2jsonld(root, content)
        } else if (is_float(clsname)) {
            jsonlds = float2jsonld(root, content)
        } else if (is_bool(clsname)) {
            jsonlds = bool2jsonld(root, content)
        } else if (is_map(clsname)) {
            jsonlds = map2jsonld(root, content)
        } else if (is_list(clsname)) {
            jsonlds = list2jsonld(root, content)
        } else {
            jsonlds = obj2jsonld(root, content)
        }

        if (jsonlds != null) {
            jsonldobj = jsonlds[0].clone()
            def jsonldcontextobj = jsonconfigobj.get("@context", [:])
            jsonldcontextobj.putAll(jsonlds[1])
            jsonldobj.putAll(jsonconfigobj)
        }
    }

    public String toString(){
        return new JsonBuilder(jsonldobj).toString()
    }

    public String toPrettyString(){
        return new JsonBuilder(jsonldobj).toPrettyString()
    }

    def getClassLastName(obj){
        if (obj == null)
            return "null"
        return obj.class.toString().split("\\.")[-1].toLowerCase()
    }

    def bool2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        def jsonldcontextobj = ["@type":"xsd:boolean"]
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }
        return [jsonobj, jsonldcontextobj] as Object[]
    }

//    configKeywords.eachWithIndex { String keywordEntry, int j ->
//        def configPath = root + "." + keywordEntry
//        def jsonconfig = getJsonObj(configPath)
//        if (jsonconfig != null)
//        // update jsonobj
//            jsonldcontextobj.putAll(jsonconfig)
//    }

    def int2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        def jsonldcontextobj = ["@type":"xsd:integer"]
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }
        return [jsonobj, jsonldcontextobj] as Object[]
    }

    def float2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        def jsonldcontextobj = ["@type":"xsd:double"]
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }
        return [jsonobj, jsonldcontextobj] as Object[]
    }

    def str2jsonld(root, str) {
        def json = new JsonBuilder(str).toString()
        def jsonobj = JSONUtils.fromString(json)
        def jsonldcontextobj = [:]
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }
        return [jsonobj, jsonldcontextobj] as Object[]
    }

    def list2jsonld(root, list) {
        def json = new JsonBuilder(list).toString()
        def jsonobj = JSONUtils.fromString(json)
        def jsonldcontextobj = ["@container":"@list"]
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }
        return [jsonobj, jsonldcontextobj] as Object[]
    }

    def map2jsonld(root, Map map) {
        def json = new JsonBuilder(map).toString()
        def jsonld = JSONUtils.fromString(json)
        def jsonldcontextobj = ["@container":"@index"]
        map.eachWithIndex { Map.Entry<Object, Object> entry, int i ->
            def propertyroot = root + "." + entry.key
            def valclsname = getClassLastName(entry.value)
            Object[] jsonlds = null
            if (is_null(valclsname)) {
                // TODO: what to do with null in map?
            } else if (is_bool(valclsname)) {
                jsonlds = bool2jsonld(propertyroot, entry.value)
            } else if (is_str(valclsname)) {
                jsonlds = str2jsonld(propertyroot, entry.value)
            } else if (is_int(valclsname)) {
                jsonlds = int2jsonld(propertyroot, entry.value)
            } else if (is_float(valclsname)) {
                jsonlds = float2jsonld(propertyroot, entry.value)
            } else if (is_map(valclsname)) {
                jsonlds = map2jsonld(propertyroot, entry.value)
            } else if (is_list(valclsname)) {
                jsonlds = list2jsonld(propertyroot, entry.value)
            } else {
                jsonlds = obj2jsonld(propertyroot, entry.value)
            }
            if (jsonlds != null) {
                jsonld.put(entry.key, jsonlds[0])
                jsonldcontextobj.put(entry.key, jsonlds[1])
            }
        }
        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }
        return [jsonld, jsonldcontextobj] as Object[]
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
//        println "-------json------"
//        println json
        def jsonld = JSONUtils.fromString(json)
        def jsonldcontextobj = [:]

        obj.getProperties().eachWithIndex { Map.Entry<Object, Object> entry, int i ->
            if (!ignoredProperties.contains(entry.key)) {
                def propertyroot = root + "." + entry.key
                def valclsname = getClassLastName(entry.value)
                Object[] jsonlds = null
                if (is_null(valclsname)) {
                    // TODO: what to do with null in map?
                } else if (is_bool(valclsname)) {
                    jsonlds = bool2jsonld(propertyroot, entry.value)
                } else if (is_str(valclsname)) {
                    jsonlds = str2jsonld(propertyroot, entry.value)
                } else if (is_int(valclsname)) {
                    jsonlds = int2jsonld(propertyroot, entry.value)
                } else if (is_float(valclsname)) {
                    jsonlds = float2jsonld(propertyroot, entry.value)
                } else if (is_map(valclsname)) {
                    jsonlds = map2jsonld(propertyroot, entry.value)
                } else if (is_list(valclsname)) {
                    jsonlds = list2jsonld(propertyroot, entry.value)
                } else {
                    jsonlds = obj2jsonld(propertyroot, entry.value)
                }
                if (jsonlds != null) {
                    jsonld.put(entry.key, jsonlds[0])
                    jsonldcontextobj.put(entry.key, jsonlds[1])
                }
            }
        }

        configKeywords.eachWithIndex { String keywordEntry, int j ->
            def configPath = root + "." + keywordEntry
            def jsonconfig = getJsonObj(configPath)
            if (jsonconfig != null)
            // update jsonobj
                jsonldcontextobj.putAll(jsonconfig)
        }

        return [jsonld, jsonldcontextobj] as Object[]
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

