package org.spauk.weatheralert.alert

import org.spauk.weatheralert.alertsettings.model.AlertSettings
import org.spauk.weatheralert.alertsettings.model.AlertTriggers
import org.spauk.weatheralert.weatherprovider.model.Forecast
import spock.lang.Specification

import java.time.Instant
import java.util.stream.Collectors
import java.util.stream.Stream

class AlertGeneratorImplSpec extends Specification {

    def generator = new AlertGeneratorImpl()

    def "should generate min and max temp alerts"() {
        given:
        def location = "location"

        and: "two data points"

        def timestampOne = Instant.ofEpochMilli(1000)
        def temperatureOne = 10.1d

        def timestampTwo = Instant.ofEpochMilli(2000)
        def temperatureTwo = -20.2d

        and: "forecast with these two datapoints"
        def forecast = Optional.of(Forecast.builder()
                                           .location(location)
                                           .dataPoints(Stream.of(Forecast.DataPoint.builder()
                                                                         .timestamp(timestampOne)
                                                                         .temperature(temperatureOne)
                                                                         .build(),
                                                                 Forecast.DataPoint.builder()
                                                                         .timestamp(timestampTwo)
                                                                         .temperature(temperatureTwo)
                                                                         .build()).collect(Collectors.toList()))
                                           .build());

        and: "alert triggers"
        def minTemperatureThreshold = -10.1d
        def maxTemperatureThreshold = 5.5d

        def alertSettings = AlertSettings.builder()
                                         .location(location)
                                         .alertTriggers(AlertTriggers.builder()
                                                                     .minTemperature(minTemperatureThreshold)
                                                                     .maxTemperature(maxTemperatureThreshold)
                                                                     .build())
                                         .build();

        when:
        def alert = generator.generateAlert(forecast, alertSettings)

        then:
        alert.getLocation() == location

        and: "alert triggers are set"
        with(alert.getAlertTriggers()) {
            getMinTemperature() == minTemperatureThreshold
            getMaxTemperature() == maxTemperatureThreshold
        }


        and: "first data point alerts on max temp exceeded"
        with(alert.getDataPoints()[0]) {
            getTimestamp() == timestampOne
            getTemperature() == temperatureOne
            isMinTemperatureExceeded() == false
            isMaxTemperatureExceeded() == true
        }

        and: "second data point alerts on min temp exceeded"
        with(alert.getDataPoints()[1]) {
            getTimestamp() == timestampTwo
            getTemperature() == temperatureTwo
            isMinTemperatureExceeded() == true
            isMaxTemperatureExceeded() == false
        }
    }

    def "should generate alert report with no datapoints if forecast is empty"() {
        given:
        def location = "location"

        and: "no forecast found for location"
        def forecast = Optional.empty();

        and: "alert triggers"
        def minTemperatureThreshold = -10.1d
        def maxTemperatureThreshold = 5.5d

        def alertSettings = AlertSettings.builder()
                                         .location(location)
                                         .alertTriggers(AlertTriggers.builder()
                                                                     .minTemperature(minTemperatureThreshold)
                                                                     .maxTemperature(maxTemperatureThreshold)
                                                                     .build())
                                         .build();

        when:
        def alert = generator.generateAlert(forecast, alertSettings)

        then:
        alert.getLocation() == location
        with(alert.getAlertTriggers()) {
            getMinTemperature() == minTemperatureThreshold
            getMaxTemperature() == maxTemperatureThreshold
        }
        alert.getDataPoints().size() == 0
    }
}