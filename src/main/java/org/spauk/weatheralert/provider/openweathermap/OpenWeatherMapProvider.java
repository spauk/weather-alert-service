package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.Forecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OpenWeatherMapProvider implements WeatherProvider {

    private final RestTemplate restTemplate;

    private final Converter converter;

    private final String appid;

    public OpenWeatherMapProvider(@Autowired RestTemplate restTemplate,
                                  @Autowired Converter converter,
                                  @Value("${provider.openweathermap.appid}") String appid) {
        this.restTemplate = restTemplate;
        this.converter = converter;
        this.appid = appid;
    }

    @Override
    public Set<Forecast> getFiveDayForecasts(Set<String> locations) {

        return locations.parallelStream()
                        .map(locationToNativeForecast())
                        .peek(nativeForecast -> LOGGER.info(nativeForecast.toString()))
                        .map(converter.nativeForecastToCanonical())
                        .collect(Collectors.toSet());
    }

    private Function<String, OpenWeatherMapForecast> locationToNativeForecast() {
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

            OpenWeatherMapForecast response = restTemplate.getForObject(url, OpenWeatherMapForecast.class);
            return response;
        };
    }
}