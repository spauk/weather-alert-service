package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.Alert;
import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.spauk.weatheralert.provider.model.Forecast;

import java.util.Optional;

public interface AlertGenerator {

    Alert generateAlert(Optional<Forecast> forecast, AlertSettings alertSettings);
}
