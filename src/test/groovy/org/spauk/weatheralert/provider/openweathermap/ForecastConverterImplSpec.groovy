package org.spauk.weatheralert.provider.openweathermap

import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast
import spock.lang.Specification

import java.time.Instant
import java.util.stream.Collectors
import java.util.stream.Stream

class ForecastConverterImplSpec extends Specification {

    def converter = new ForecastConverterImpl()

    def "should convert native forecast to canonical"() {
        given:
        def location = "location"

        def firstTimestamp = Instant.ofEpochMilli(1000)
        def firstTemperature = 1.1d

        def secondTimestamp = Instant.ofEpochMilli(2000)
        def secondTemperature = 2.2d

        def nativeForecast = OpenWeatherMapForecast
        .builder()
        .city(OpenWeatherMapForecast.City
                                    .builder()
                                    .name(location)
                                    .build())
        .list(Stream.of(OpenWeatherMapForecast.ListElement
                                              .builder()
                                              .main(OpenWeatherMapForecast.ListElement.Main
                                                                          .builder()
                                                                          .temp(firstTemperature)
                                                                          .build())
                                              .dt(firstTimestamp)
                                              .build(),
                        OpenWeatherMapForecast.ListElement
                                              .builder()
                                              .main(OpenWeatherMapForecast.ListElement.Main
                                                                          .builder()
                                                                          .temp(secondTemperature)
                                                                          .build())
                                              .dt(secondTimestamp)
                                              .build())
                    .collect(Collectors.toList()))
        .build();

        when:
        def canonicalForecast = converter.convertToCanonicalForecast(nativeForecast)

        then:
        canonicalForecast.location == location

        canonicalForecast.dataPoints.size() == 2

        with(canonicalForecast.getDataPoints()[0]) {
            getTimestamp() == firstTimestamp
            getTemperature() == firstTemperature
        }

        with(canonicalForecast.getDataPoints()[1]) {
            getTimestamp() == secondTimestamp
            getTemperature() == secondTemperature
        }
    }
}
