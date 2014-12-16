package org.lappsgrid.serialization

import com.github.fge.jsonschema.core.report.ProcessingMessage
import com.github.fge.jsonschema.core.report.ProcessingReport
import org.anc.json.validator.Validator
import org.junit.*
import org.lappsgrid.serialization.aas.Token
import org.lappsgrid.serialization.datasource.Get
import org.lappsgrid.serialization.datasource.GetMetadata
import org.lappsgrid.serialization.datasource.List
import org.lappsgrid.serialization.lif.Container
import org.lappsgrid.serialization.service.Execute

import static org.junit.Assert.*

/**
 * @author Keith Suderman
 */
class ValidationTests {

    @Test
    void validateToken() {
        Token token = TokenFactory.createToken()
        String json = Serializer.toJson(token)
        validate(json, 'token.json')
    }

    @Test
    void validateGet() {
        Token token = TokenFactory.createToken()
        String json = Serializer.toPrettyJson(new Get(token, "key"))
        validate(json, "action/get.json")
    }

    @Test
    void validateListNoArgs() {
        Token token = TokenFactory.createToken()
        String json = Serializer.toPrettyJson(new List(token))
        validate(json, "action/list.json")
    }

    @Test
    void  validateListWithArgs() {
        final int start = 1
        final int end = 2
        Token token = TokenFactory.createToken()
        List list = new List(token, start, end)
        String json = Serializer.toPrettyJson(list)
        validate(json, 'action/list.json')
    }

    @Test
    void validateExecute() {
        Token token = TokenFactory.createToken()
        Container container = ContainerFactory.createContainer()
        Execute execute = new Execute(token, container)
        String json = Serializer.toPrettyJson(execute)
        validate(json, "action/execute.json")
    }

    @Test
    void validateGetMetadata() {
        Token token = TokenFactory.createToken()
        GetMetadata get = new GetMetadata(token)
        String json = Serializer.toPrettyJson(get)
        validate(json, "action/getmetadata.json")
    }

    @Test
    void validateContainer() {
        Container container = ContainerFactory.createContainer()
        String json = Serializer.toPrettyJson(container)
        validate(json, "lif.json")
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
