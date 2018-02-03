package org.spauk.weatheralert;

import org.spauk.weatheralert.alert.AlertSettingsRepository;
import org.spauk.weatheralert.alert.model.AlertSettings;
import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.Forecast;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherUpdateService {

    private final WeatherProvider weatherProvider;

    private final AlertSettingsRepository alertSettingsRepository;

    @Scheduled(fixedRate = 10000)
    public void update() {
        Set<AlertSettings> alertSettings = alertSettingsRepository.findAll();

        Set<String> locations = alertSettings.stream()
                                             .map(AlertSettings::getLocation)
                                             .collect(Collectors.toSet());

        Set<Forecast> forecasts = weatherProvider.getFiveDayForecasts(locations);

        forecasts.forEach(forecast -> LOGGER.info(forecast.toString()));
    }
}