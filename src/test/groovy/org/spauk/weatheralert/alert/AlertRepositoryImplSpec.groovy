package org.spauk.weatheralert.alert

import org.spauk.weatheralert.alert.model.AlertSummary
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class AlertRepositoryImplSpec extends Specification {

    def eventPublisher = Mock(ApplicationEventPublisher)

    def repository = new InMemoryAlertRepository(eventPublisher)

    def "should save the latest alert summary"() {
        given:
        def alertSummary = Mock(AlertSummary)

        when:
        repository.saveAlertSummary(alertSummary)

        then:
        def latestSummary = repository.getLatestAlertSummary()
        latestSummary.get() == alertSummary
    }

    def "empty repository should return an empty optional for latest alert summary"() {
        when:
        def latestSummary = repository.getLatestAlertSummary()

        then:
        latestSummary != null
        !latestSummary.isPresent()
    }

    def "should dispatch an event once alert summary is saved"() {
        given:
        def alertSummary = Mock(AlertSummary)

        when:
        repository.saveAlertSummary(alertSummary)

        then:
        1 * eventPublisher.publishEvent(alertSummary)
    }
}