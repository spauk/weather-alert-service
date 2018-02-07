package org.spauk.weatheralert.weatherprovider.openweathermap;

import org.spauk.weatheralert.weatherprovider.openweathermap.model.OpenWeatherMapForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OpenWeatherMapHttpImpl implements OpenWeatherMapClient {

    private final RestTemplate restTemplate;

    private final String applicationId;

    public OpenWeatherMapHttpImpl(@Autowired RestTemplate restTemplate,
                                  @Value("${service.weather-provider.openweathermap.application-id}") String applicationId) {
        this.restTemplate = restTemplate;
        this.applicationId = applicationId;
    }

    @Override
    public OpenWeatherMapForecast getForecastForLocation(String location) {
        String url = UriComponentsBuilder.newInstance()
                                         .scheme("http")
                                         .host("api.openweathermap.org")
                                         .path("/data/2.5/forecast")
                                         .queryParam("q", location)
                                         .queryParam("units", "metric")
                                         .queryParam("appid", applicationId)
                                         .build()
                                         .toUriString();

        LOGGER.info("Executing GET request: {}", url);

        return restTemplate.getForObject(url, OpenWeatherMapForecast.class);
    }
}