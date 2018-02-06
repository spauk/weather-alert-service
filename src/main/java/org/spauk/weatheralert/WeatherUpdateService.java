package org.spauk.weatheralert;

import org.spauk.weatheralert.alert.AlertRepository;
import org.spauk.weatheralert.alert.AlertGenerator;
import org.spauk.weatheralert.alert.model.Alert;
import org.spauk.weatheralert.alertsettings.AlertSettingsRepository;
import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.Forecast;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherUpdateService {

    // location, location alert settings (al set repo), location forecast (provider sv), location alert (alert sv)

    private final WeatherProvider weatherProvider;

    private final AlertSettingsRepository alertSettingsRepository;

    private final AlertRepository alertRepository;

    private final AlertGenerator alertGenerator;

    @Scheduled(fixedDelayString = "${service.polling-interval-ms}")
    public void update() {

        Set<AlertSettings> alertSettings = alertSettingsRepository.getAll();

        Set<Alert> alerts = alertSettings.parallelStream()
                                         .map(this::generateAlertForLocation)
                                         .collect(Collectors.toSet());

        alertRepository.updateSummary(alerts);
    }

    private Alert generateAlertForLocation(AlertSettings alertSettings) {
        String location = alertSettings.getLocation();
        Optional<Forecast> forecast = weatherProvider.getForecastForLocation(location);
        return alertGenerator.generateAlert(forecast, alertSettings);
    }
}