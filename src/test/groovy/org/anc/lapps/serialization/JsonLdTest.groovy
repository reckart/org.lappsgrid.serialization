package org.anc.lapps.serialization

import com.github.jsonldjava.utils.JSONUtils
import org.anc.io.FileUtils
import org.anc.resource.ResourceLoader
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import com.github.jsonldjava.core.JsonLdApi
import com.github.jsonldjava.core.JsonLdOptions
import com.github.jsonldjava.core.JsonLdProcessor
import com.github.jsonldjava.utils.JSONUtils
import org.anc.lapps.serialization.Annotation
import static org.junit.Assert.*

import org.anc.lapps.serialization.JsonLd


class JsonLdTest {


    @Before
    public void setup()
    {

    }

    @After
    public void tearDown()
    {

    }
    @Test
    public void testInit(){
        println '<-------- JsonLdTest ---------'
        JsonLd jld = new JsonLd()
        Annotation antn = new Annotation()
        antn.start = 0
        antn.end = 20
        antn.features.put("Text","How are you today?")
        antn.metadata.put("Features", antn.features)
        def jld2 = new JsonLd(antn)
        println "-----toString-----"
        println jld2.toString()

        jld2.isValid()
//        jld2.obj2jsonld(antn)
        println '----------------------------->'
    }
}
