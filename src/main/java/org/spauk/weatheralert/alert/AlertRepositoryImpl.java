package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSummary;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AlertRepositoryImpl implements AlertRepository {

    private final ApplicationEventPublisher eventPublisher;

    private volatile AlertSummary latestAlertSummary;

    @Override
    public void updateSummary(AlertSummary alertSummary) {

        latestAlertSummary = alertSummary;

        eventPublisher.publishEvent(alertSummary);
    }

    @Override
    public Optional<AlertSummary> getLatestSummary() {
        return Optional.ofNullable(latestAlertSummary);
    }
}
