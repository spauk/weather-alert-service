package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.Forecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherMapProvider implements WeatherProvider {

    private final OpenWeatherMapClient client;

    private final ForecastConverter converter;

    @Override
    public Set<Forecast> getFiveDayForecasts(Set<String> locations) {

        return locations.parallelStream()
                        .map(this::getForecastForLocation)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
    }

    private Optional<Forecast> getForecastForLocation(String location) {
        try {
            OpenWeatherMapForecast nativeForecast = client.getFiveDayForecastForLocation(location);
            Forecast canonicalForecast = converter.convertToCanonicalForecast(nativeForecast);
            return Optional.of(canonicalForecast);
        } catch (Exception e) {
            LOGGER.error("Failed to get a forecast for location: {}", location, e);
        }
        return Optional.empty();
    }
}