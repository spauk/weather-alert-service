package org.spauk.weatheralert.weatherprovider;

import org.spauk.weatheralert.weatherprovider.model.Forecast;

import java.util.Optional;

/**
 * Interface describing weather information weatherprovider.
 */
public interface WeatherProvider {

    /**
     * Returns a weather forecast for the given location
     *
     * @param location
     * @return non-empty weather forecast optional in case location is recognized and location forecast is found, otherwise returns an empty optional
     */
    Optional<Forecast> getForecastForLocation(String location);
}