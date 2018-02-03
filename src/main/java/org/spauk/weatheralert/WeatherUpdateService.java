package org.spauk.weatheralert;

import org.spauk.weatheralert.alert.AlertSettingsRepository;
import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.LocationForecast;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Set<LocationForecast> forecasts = weatherProvider.getLocationForecasts(Stream.of("espoo", "alicante")
                                                                                     .collect(Collectors.toSet()));

        forecasts.forEach(forecast -> LOGGER.info(forecast.toString()));
    }
}