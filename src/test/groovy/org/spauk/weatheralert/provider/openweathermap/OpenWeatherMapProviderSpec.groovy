package org.spauk.weatheralert.provider.openweathermap

import org.spauk.weatheralert.provider.model.Forecast
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast
import spock.lang.Specification

@SuppressWarnings("GroovyAssignabilityCheck")
class OpenWeatherMapProviderSpec extends Specification {

    def client = Mock(OpenWeatherMapClient)

    def converter = Mock(ForecastConverter)

    def provider = new OpenWeatherMapProvider(client, converter)

    def locationOne = "location-1"

    def locationTwo = "location-2"

    def nativeForecastOne = Mock(OpenWeatherMapForecast)

    def nativeForecastTwo = Mock(OpenWeatherMapForecast)

    def canonicalForecastOne = Mock(Forecast)

    def canonicalForecastTwo = Mock(Forecast)

    def "should fetch and convert forecasts"() {
        given:
        def locations = [locationOne, locationTwo] as Set

        and: "client returns mock native forecast"
        client.getFiveDayForecastForLocation(locationOne) >> nativeForecastOne
        client.getFiveDayForecastForLocation(locationTwo) >> nativeForecastTwo

        and: "converter returns mock canonical forecast"
        converter.convertToCanonicalForecast(nativeForecastOne) >> canonicalForecastOne
        converter.convertToCanonicalForecast(nativeForecastTwo) >> canonicalForecastTwo

        when:
        def forecasts = provider.getFiveDayForecasts(locations as Set)

        then:
        forecasts == [canonicalForecastOne, canonicalForecastTwo] as Set
    }

    def "should continue processing if one location fetching fails"() {
        given:
        def locations = [locationOne, locationTwo] as Set

        and: "clint fails to return first location forecast"
        client.getFiveDayForecastForLocation(locationOne) >> { throw new RuntimeException() }

        and: "client returns second location mock forecast"
        client.getFiveDayForecastForLocation(locationTwo) >> nativeForecastTwo

        and: "converter returns mock canonical forecast"
        converter.convertToCanonicalForecast(nativeForecastTwo) >> canonicalForecastTwo

        when:
        def forecasts = provider.getFiveDayForecasts(locations)

        then:
        forecasts == [canonicalForecastTwo] as Set
    }

    def "should continue processing if one location conversion fails"() {
        given:
        def locations = [locationOne, locationTwo] as Set

        and: "client returns mock native forecast"
        client.getFiveDayForecastForLocation(locationOne) >> nativeForecastOne
        client.getFiveDayForecastForLocation(locationTwo) >> nativeForecastTwo

        and: "converter fails to convert first native forecast"
        converter.convertToCanonicalForecast(nativeForecastOne) >> { throw new RuntimeException() }

        and: "converter converts second location forecast"
        converter.convertToCanonicalForecast(nativeForecastTwo) >> canonicalForecastTwo

        when:
        def forecasts = provider.getFiveDayForecasts([locationOne, locationTwo] as Set)

        then:
        forecasts == [canonicalForecastTwo] as Set
    }
}
