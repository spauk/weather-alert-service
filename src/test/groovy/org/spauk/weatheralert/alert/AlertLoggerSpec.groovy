package org.spauk.weatheralert.alert

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import org.spauk.weatheralert.alert.model.AlertSummary
import spock.lang.Specification

@SuppressWarnings("GroovyAssignabilityCheck")
class AlertLoggerSpec extends Specification {

    def objectMapper = Mock(ObjectMapper)

    def objectWriter = Mock(ObjectWriter)

    def logFilePath = "path"

    def logger = new AlertLogger(objectMapper, logFilePath)

    def "should write event payload to log file using ObjectMapper"() {
        given:
        def alertSummary = Mock(AlertSummary)

        when:
        logger.onEvent(alertSummary)

        then: "default pretty printer is created"
        1 * objectMapper.writerWithDefaultPrettyPrinter() >> objectWriter

        and: "file is written with correct path and payload"
        1 * objectWriter.writeValue(_, alertSummary) >> { args ->
            File file = args[0]
            assert file.getPath() == logFilePath
        }
    }

    def "should catch ObjectMapper exceptions"() {
        given:
        def alertSummary = Mock(AlertSummary)
        objectMapper.writerWithDefaultPrettyPrinter() >> objectWriter

        when:
        logger.onEvent(alertSummary)

        then: "object mapper is called and it fails"
        1 * objectWriter.writeValue(*_) >> { throw new RuntimeException() }

        and: "object mapper exception is caught"
        noExceptionThrown()
    }
}
