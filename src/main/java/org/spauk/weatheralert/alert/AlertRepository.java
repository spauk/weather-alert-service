package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSummary;

import java.util.Optional;

public interface AlertRepository {

    void saveAlertSummary(AlertSummary alertSummary);

    Optional<AlertSummary> getLatestAlertSummary();
}