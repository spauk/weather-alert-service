package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.Alert;
import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.spauk.weatheralert.alertsettings.model.AlertTriggers;
import org.spauk.weatheralert.provider.model.Forecast;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AlertGeneratorImpl implements AlertGenerator {

    @Override
    public Alert generateAlert(Optional<Forecast> forecast, AlertSettings alertSettings) {
        AlertTriggers alertTriggers = alertSettings.getAlertTriggers();
        return Alert.builder()
                    .location(alertSettings.getLocation())
                    .alertTriggers(alertSettings.getAlertTriggers())
                    .dataPoints(convertToAlertDatapoints(forecast, alertTriggers))
                    .build();
    }

    private List<Alert.DataPoint> convertToAlertDatapoints(Optional<Forecast> forecast, AlertTriggers alertTriggers) {
        return forecast.map(Forecast::getDataPoints)
                       .orElse(Collections.emptyList())
                       .stream()
                       .map(forecastDataPoint -> convertToAlertDatapoint(forecastDataPoint, alertTriggers))
                       .collect(Collectors.toList());
    }

    private Alert.DataPoint convertToAlertDatapoint(Forecast.DataPoint forecastDataPoint, AlertTriggers alertTriggers) {
        return Alert.DataPoint.builder()
                              .timestamp(forecastDataPoint.getTimestamp())
                              .temperature(forecastDataPoint.getTemperature())
                              .minTemperatureExceeded(isMinTemperatureExceeded(forecastDataPoint.getTemperature(), alertTriggers.getMinTemperature()))
                              .maxTemperatureExceeded(isMaxTemperatureExceeded(forecastDataPoint.getTemperature(), alertTriggers.getMaxTemperature()))
                              .build();
    }

    private boolean isMinTemperatureExceeded(double temperature, double minTemperature) {
        return temperature < minTemperature;
    }

    private boolean isMaxTemperatureExceeded(double temperature, double maxTemperature) {
        return temperature > maxTemperature;
    }
}