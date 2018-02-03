package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSettings;

import java.util.Set;

public interface AlertSettingsRepository {

    Set<AlertSettings> findAll();
}