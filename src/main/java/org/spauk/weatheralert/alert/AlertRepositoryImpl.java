package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.Alert;
import org.spauk.weatheralert.alert.model.AlertSummaryUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Set;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlertRepositoryImpl implements AlertRepository {

    private final ApplicationEventPublisher eventPublisher;

    private volatile Set<Alert> latestAlertSummary = Collections.emptySet();

    @Override
    public void updateSummary(Set<Alert> alerts) {

        latestAlertSummary = alerts;

        eventPublisher.publishEvent(AlertSummaryUpdatedEvent.builder()
                                                            .latestAlertSummary(alerts)
                                                            .build());
    }

    @Override
    public Set<Alert> getLatestSummary() {
        return latestAlertSummary;
    }
}
