package org.spauk.weatheralert.provider;

import org.spauk.weatheralert.provider.model.Forecast;

import java.util.Set;

public interface WeatherProvider {

    Set<Forecast> getFiveDayForecasts(Set<String> locations);
}