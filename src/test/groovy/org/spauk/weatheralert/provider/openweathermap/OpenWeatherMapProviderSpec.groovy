package org.spauk.weatheralert.provider.openweathermap

import org.spauk.weatheralert.provider.model.Forecast
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast
import spock.lang.Specification

@SuppressWarnings("GroovyAssignabilityCheck")
class OpenWeatherMapProviderSpec extends Specification {

    def client = Mock(OpenWeatherMapClient)

    def converter = Mock(ForecastConverter)

    def provider = new OpenWeatherMapProvider(client, converter)

    def "should fetch and convert forecasts"() {
        given: "locations"
        def locationOne = "location-1"
        def locationTwo = "location-2"
        def locations = [locationOne, locationTwo] as Set

        and: "native forecast mocks"
        def nativeForecastOne = Mock(OpenWeatherMapForecast)
        def nativeForecastTwo = Mock(OpenWeatherMapForecast)

        and: "converted forecast mocks"
        def canonicalForecastOne = Mock(Forecast)
        def canonicalForecastTwo = Mock(Forecast)

        and: "client returns mock native forecast"
        client.getFiveDayForecastForLocation(locationOne) >> nativeForecastOne
        client.getFiveDayForecastForLocation(locationTwo) >> nativeForecastTwo

        and: "converter returns mock canonical forecast"
        converter.convertToCanonicalForecast(nativeForecastOne) >> canonicalForecastOne
        converter.convertToCanonicalForecast(nativeForecastTwo) >> canonicalForecastTwo

        when:
        def forecasts = provider.getFiveDayForecasts(locations)

        then:
        forecasts == [canonicalForecastOne, canonicalForecastTwo] as Set
    }
}
