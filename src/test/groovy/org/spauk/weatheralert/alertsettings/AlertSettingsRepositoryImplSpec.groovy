package org.spauk.weatheralert.alertsettings

import com.fasterxml.jackson.databind.ObjectMapper
import org.spauk.weatheralert.alertsettings.model.AlertSettings
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import spock.lang.Specification

class AlertSettingsRepositoryImplSpec extends Specification {

    def objectMapper = Mock(ObjectMapper)

    def resourceLoader = Mock(ResourceLoader)

    def filePath = "file path"

    def repository = new AlertSettingsRepositoryImpl(objectMapper, resourceLoader, filePath)

    def "should load alert settings from resource and deserialize with ObjectMapper"() {
        given:
        def mockResource = Mock(Resource)
        def mockInputStream = Mock(InputStream)
        resourceLoader.getResource(filePath) >> mockResource
        mockResource.getInputStream() >> mockInputStream

        and:
        def mockSettingsOne = Mock(AlertSettings)
        def mockSettingsTwo = Mock(AlertSettings)

        and:
        objectMapper.readValue(mockInputStream, AlertSettings[]) >> { [mockSettingsOne, mockSettingsTwo] as AlertSettings[] }

        when:
        def actualAllAlertSettings = repository.getAllSettings()

        then:
        actualAllAlertSettings == [mockSettingsOne, mockSettingsTwo] as Set
    }

    def "should rethrow RuntimeException when ObjectMapper fails with checked IOException"() {
        given:
        def mockResource = Mock(Resource)
        def mockInputStream = Mock(InputStream)
        resourceLoader.getResource(filePath) >> mockResource
        mockResource.getInputStream() >> mockInputStream

        and:
        def ioException = new IOException()
        objectMapper.readValue(mockInputStream, AlertSettings[]) >> { throw ioException }

        when:
        repository.getAllSettings()

        then: "rethrows RuntimeException and preserves the original cause"
        RuntimeException e = thrown()
        e.getCause() == ioException
    }
}