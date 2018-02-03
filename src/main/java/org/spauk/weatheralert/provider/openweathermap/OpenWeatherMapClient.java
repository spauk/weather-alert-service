package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;

public interface OpenWeatherMapClient {

    OpenWeatherMapForecast getFiveDayForecastForLocation(String location);
}
