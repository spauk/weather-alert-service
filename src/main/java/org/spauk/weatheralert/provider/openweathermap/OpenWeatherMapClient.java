package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;

/**
 * Client for OpenWeatherMap weather provider
 */
public interface OpenWeatherMapClient {

    /**
     * Retrieves a weather forecast for a given location
     *
     * @param location
     * @return weather forecast
     */
    OpenWeatherMapForecast getForecastForLocation(String location);
}
