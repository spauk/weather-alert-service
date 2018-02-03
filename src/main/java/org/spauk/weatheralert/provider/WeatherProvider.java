package org.spauk.weatheralert.provider;

import org.spauk.weatheralert.provider.model.LocationForecast;

import java.util.Set;

public interface WeatherProvider {

    Set<LocationForecast> getLocationForecasts(Set<String> locations);
}
