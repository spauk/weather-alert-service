package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.WeatherProvider;
import org.spauk.weatheralert.provider.model.LocationForecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;
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

    @Override
    public Set<LocationForecast> getLocationForecasts(Set<String> locations) {
        return locations.parallelStream()
                        .map(this::getNativeLocationForecast)
                        .map(this::convertToCanonicalLocationForecast)
                        .collect(Collectors.toSet());

    }

    private OpenWeatherMapForecastResponse getNativeLocationForecast(String location) {

        String url = UriComponentsBuilder.newInstance()
                                         .scheme("http")
                                         .host("api.openweathermap.org")
                                         .path("/data/2.5/forecast")
                                         .queryParam("q", location)
                                         .queryParam("units", "metric")
                                         .queryParam("appid", appid)
                                         .build()
                                         .toUriString();

        OpenWeatherMapForecastResponse response = restTemplate.getForObject(url,
                                                                            OpenWeatherMapForecastResponse.class);
        LOGGER.info(response.toString());
        return response;
    }

    private LocationForecast convertToCanonicalLocationForecast(OpenWeatherMapForecastResponse nativeForecast) {
        return LocationForecast.builder()
                               .location(nativeForecast.getCity()
                                                       .getName())
                               .dataPoints(nativeForecast.getList()
                                                         .stream()
                                                         .map(this::convertToDataPoint)
                                                         .collect(Collectors.toList()))
                               .build();
    }

    private LocationForecast.DataPoint convertToDataPoint(OpenWeatherMapForecastResponse.ListElement nativeDataPoint) {
        return LocationForecast.DataPoint.builder()
                                         .timestamp(nativeDataPoint.getDt())
                                         .temperature(nativeDataPoint.getMain()
                                                                     .getTemp())
                                         .build();
    }
}
