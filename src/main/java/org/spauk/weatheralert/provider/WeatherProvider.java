package org.spauk.weatheralert.provider;

import org.spauk.weatheralert.provider.model.Forecast;

import java.util.Optional;

public interface WeatherProvider {

    Optional<Forecast> getForecastForLocation(String location);
}