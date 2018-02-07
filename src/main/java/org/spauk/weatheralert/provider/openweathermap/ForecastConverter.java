package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.model.Forecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;

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