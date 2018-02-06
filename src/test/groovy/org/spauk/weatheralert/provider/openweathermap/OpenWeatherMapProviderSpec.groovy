package org.spauk.weatheralert.provider.openweathermap

import org.spauk.weatheralert.provider.model.Forecast
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast
import spock.lang.Specification

@SuppressWarnings("GroovyAssignabilityCheck")
class OpenWeatherMapProviderSpec extends Specification {

    def client = Mock(OpenWeatherMapClient)

    def converter = Mock(ForecastConverter)

    def provider = new OpenWeatherMapProvider(client, converter)

    def location = "location"

    def nativeForecast = Mock(OpenWeatherMapForecast)

    def canonicalForecast = Mock(Forecast)

    def "should fetch and convert forecast"() {

        given: "client returns mock native forecast"
        client.getForecastForLocation(location) >> nativeForecast

        and: "converter returns mock canonical forecast"
        converter.convertToCanonicalForecast(nativeForecast) >> canonicalForecast

        when:
        def actualForecast = provider.getForecastForLocation(location)

        then:
        actualForecast.get() == canonicalForecast
    }

    def "should return empty optional if forecast fetching fails"() {

        given: "client fails"
        client.getForecastForLocation(location) >> {throw new RuntimeException()}

        when:
        def actualForecast = provider.getForecastForLocation(location)

        then:
        !actualForecast.isPresent()
    }

    def "should reutrn empty optional if forecast conversion fails"() {

        given: "client returns mock native forecast"
        client.getForecastForLocation(location) >> nativeForecast

        and: "converter fails"
        converter.convertToCanonicalForecast(nativeForecast) >> {throw new RuntimeException()}

        when:
        def actualForecast = provider.getForecastForLocation(location)

        then:
        !actualForecast.isPresent()
    }
}
