package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.Alert;
import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.spauk.weatheralert.weatherprovider.model.Forecast;

import java.util.Optional;

/**
 * Generates possible weather alerts by applying alert triggers to a weather data.
 */
public interface AlertGenerator {

    /**
     * Generates weather forecast alert given a location forecast and alert settings
     * @param forecast location weather forecast
     * @param alertSettings location alert settings
     * @return location weather alert
     */
    Alert generateAlert(Optional<Forecast> forecast, AlertSettings alertSettings);
}
