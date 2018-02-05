package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.Alert;

import java.util.Set;

public interface AlertRepository {

    void updateSummary(Set<Alert> alerts);

    Set<Alert> getLatestSummary();
}