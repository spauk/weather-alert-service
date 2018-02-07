package org.spauk.weatheralert.weatherprovider.openweathermap;

import org.spauk.weatheralert.weatherprovider.openweathermap.model.OpenWeatherMapForecast;

/**
 * Client for OpenWeatherMap weather weatherprovider
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
