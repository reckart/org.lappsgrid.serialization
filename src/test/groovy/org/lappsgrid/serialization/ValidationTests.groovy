package org.lappsgrid.serialization

import com.github.fge.jsonschema.core.report.ProcessingMessage
import com.github.fge.jsonschema.core.report.ProcessingReport
import org.anc.json.validator.Validator
import org.junit.*
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.datasource.Get
import org.lappsgrid.serialization.datasource.List
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.service.Execute

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class ValidationTests {

    @Test
    void validateGet() {
//        String json = Serializer.toPrettyJson(new Get(token, "key"))
        String json = new Get("key").asJson()
        validate(json, "action/get-schema.json")
    }

    @Test
    void validateListNoArgs() {
        String json = Serializer.toPrettyJson(new List())
        validate(json, "action/list-schema.json")
    }

    @Test
    void  validateListWithArgs() {
        final int start = 1
        final int end = 2
        List list = new List(start, end)
//        String json = Serializer.toPrettyJson(list)
        validate(list.asJson(), 'action/list-schema.json')
    }

    @Test
    void validateExecute() {
        Container container = ContainerFactory.createContainer()
        Execute execute = new Execute(container)
//        String json = Serializer.toPrettyJson(execute)
        validate(execute.asJson(), "action/execute-schema.json")
    }

    @Test
    void validateContainer() {
        Container container = ContainerFactory.createContainer()
        String json = Serializer.toPrettyJson(container)
        validate(json, "lif-schema.json")
    }

    void validate(String json, String schemaName) {
        Validator validator = getValidator(schemaName)
        ProcessingReport result = validator.validate(json)
        if (!result.success) {
            println json
            result.asList().each { ProcessingMessage message ->
                println message.message
            }
            fail('Validation failed')
        }
    }

    private Validator getValidator(String name) {
        return new Validator(new URL("http://vocab.lappsgrid.org/schema/$name"))
    }

}
