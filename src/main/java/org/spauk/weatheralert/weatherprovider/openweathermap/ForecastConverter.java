package org.spauk.weatheralert.weatherprovider.openweathermap;

import org.spauk.weatheralert.weatherprovider.model.Forecast;
import org.spauk.weatheralert.weatherprovider.openweathermap.model.OpenWeatherMapForecast;

/**
 * Converter for OpenWeatherMap data objects into application canonical data objects.
 */
public interface ForecastConverter {

    /**
     * Converts OpenWeatherMap weather forecast into canonical application forecast
     *
     * @param nativeForecast OpenWeatherMap forecast
     * @return canonical forecast
     */
    Forecast convertToCanonicalForecast(OpenWeatherMapForecast nativeForecast);
}