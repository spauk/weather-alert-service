package org.spauk.weatheralert;

import org.spauk.weatheralert.alert.AlertGenerator;
import org.spauk.weatheralert.alert.AlertRepository;
import org.spauk.weatheralert.alert.model.Alert;
import org.spauk.weatheralert.alert.model.AlertSummary;
import org.spauk.weatheralert.alertsettings.AlertSettingsRepository;
import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.Forecast;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherAlertService {

    private final WeatherProvider weatherProvider;

    private final AlertSettingsRepository alertSettingsRepository;

    private final AlertRepository alertRepository;

    private final AlertGenerator alertGenerator;

    @Scheduled(fixedDelayString = "${service.weather-provider.polling-interval-ms}")
    public void generateAlert() {
        try {

            doGenerateAlert();

        } catch (Exception e) {
            LOGGER.error("Failed to generate a weather alert", e);
        }
    }

    private void doGenerateAlert() {
        Set<AlertSettings> alertSettings = alertSettingsRepository.getAllSettings();

        Set<Alert> alerts = generateAlerts(alertSettings);

        AlertSummary alertSummary = generateAlertSummary(alerts);

        alertRepository.saveAlertSummary(alertSummary);
    }

    private Set<Alert> generateAlerts(Set<AlertSettings> alertSettings) {
        return alertSettings.parallelStream()
                            .map(this::generateAlertForLocation)
                            .collect(Collectors.toSet());
    }

    private Alert generateAlertForLocation(AlertSettings alertSettings) {
        String location = alertSettings.getLocation();
        Optional<Forecast> forecast = weatherProvider.getForecastForLocation(location);
        return alertGenerator.generateAlert(forecast, alertSettings);
    }

    private AlertSummary generateAlertSummary(Set<Alert> alerts) {
        return AlertSummary.builder()
                           .timestamp(Instant.now())
                           .alerts(alerts)
                           .build();
    }
}