package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.LocationForecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapLocationForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenWeatherMapProvider implements WeatherProvider {

    private final RestTemplate restTemplate;

    @Value("${provider.openweathermap.appid}")
    public String appid;

    private final OpenWeatherMapConverter converter;

    @Override
    public Set<LocationForecast> getLocationForecasts(Set<String> locations) {
        return locations.parallelStream()
                        .map(locationToNativeForecast())
                        .peek(nativeForecast -> LOGGER.info(nativeForecast.toString()))
                        .map(converter.nativeForecastToCanonical())
                        .collect(Collectors.toSet());
    }

    private Function<String, OpenWeatherMapLocationForecast> locationToNativeForecast() {
        return location -> {

            String url = UriComponentsBuilder.newInstance()
                                             .scheme("http")
                                             .host("api.openweathermap.org")
                                             .path("/data/2.5/forecast")
                                             .queryParam("q", location)
                                             .queryParam("units", "metric")
                                             .queryParam("appid", appid)
                                             .build()
                                             .toUriString();

            OpenWeatherMapLocationForecast response = restTemplate.getForObject(url, OpenWeatherMapLocationForecast.class);
            return response;
        };
    }
}