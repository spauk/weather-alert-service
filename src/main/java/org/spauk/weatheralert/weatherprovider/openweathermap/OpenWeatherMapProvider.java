package org.spauk.weatheralert.weatherprovider.openweathermap;

import org.spauk.weatheralert.weatherprovider.WeatherProvider;
import org.spauk.weatheralert.weatherprovider.model.Forecast;
import org.spauk.weatheralert.weatherprovider.openweathermap.model.OpenWeatherMapForecast;
import org.springframework.stereotype.Component;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherMapProvider implements WeatherProvider {

    private final OpenWeatherMapClient client;

    private final ForecastConverter converter;

    @Override
    public Optional<Forecast> getForecastForLocation(String location) {
        try {
            OpenWeatherMapForecast nativeForecast = client.getForecastForLocation(location);
            Forecast canonicalForecast = converter.convertToCanonicalForecast(nativeForecast);
            return Optional.of(canonicalForecast);
        } catch (Exception e) {
            LOGGER.error("Failed to get a forecast for location: {}", location, e);
        }
        return Optional.empty();
    }
}