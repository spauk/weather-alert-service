package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.model.Forecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;

public interface ForecastConverter {

    Forecast convertToCanonicalForecast(OpenWeatherMapForecast nativeForecast);
}