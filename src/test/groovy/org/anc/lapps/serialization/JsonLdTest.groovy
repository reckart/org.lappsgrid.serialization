package org.anc.lapps.serialization

import com.github.jsonldjava.utils.JSONUtils
import org.anc.io.FileUtils
import org.anc.resource.ResourceLoader
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test


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
//        jld
        println '<-------- JsonLdTest ---------'

//        def sjsonld = """
//            {
//              "@context": {
//                "ical": "http://www.w3.org/2002/12/cal/ical#",
//                "xsd": "http://www.w3.org/2001/XMLSchema#",
//                "ical:dtstart": {
//                  "@type": "xsd:dateTime"
//                }
//              },
//              "ical:summary": "Lady Gaga Concert",
//              "ical:location": "New Orleans Arena, New Orleans, Louisiana, USA",
//              "ical:dtstart": "2011-04-09T20:00Z"
//            }
//        """
//        def jsonobj = JSONUtils.fromString(sjsonld)

        JsonLd jld = new JsonLd()
        Annotation antn = new Annotation()
        def jld2 = new JsonLd(antn)
        println "-----toString-----"
        println jld2.toString()
//        jld2.obj2jsonld(antn)
        println '----------------------------->'
    }
}
