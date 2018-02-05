package org.spauk.weatheralert.alertsettings;

import org.spauk.weatheralert.alertsettings.model.AlertSettings;

import java.util.Set;

public interface AlertSettingsRepository {

    Set<AlertSettings> getAll();
}