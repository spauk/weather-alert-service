package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.Forecast;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherMapProvider implements WeatherProvider {

    private final OpenWeatherMapClientImpl client;

    private final ForecastConverter converter;

    @Override
    public Set<Forecast> getFiveDayForecasts(Set<String> locations) {

        return locations.parallelStream()
                        .map(client::getFiveDayForecastForLocation)
                        .peek(nativeForecast -> LOGGER.info(nativeForecast.toString()))
                        .map(converter::convertToCanonicalForecast)
                        .collect(Collectors.toSet());
    }
}