package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.model.Forecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;

import java.util.function.Function;

public interface Converter {

    Function<OpenWeatherMapForecast, Forecast> nativeForecastToCanonical();
}
