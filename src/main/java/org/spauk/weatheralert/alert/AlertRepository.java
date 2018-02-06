package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSummary;

import java.util.Optional;

public interface AlertRepository {

    void updateSummary(AlertSummary alertSummary);

    Optional<AlertSummary> getLatestSummary();
}