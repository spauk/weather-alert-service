package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.model.LocationForecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapLocationForecast;

import java.util.function.Function;

public interface OpenWeatherMapConverter {

    Function<OpenWeatherMapLocationForecast, LocationForecast> nativeForecastToCanonical();
}
