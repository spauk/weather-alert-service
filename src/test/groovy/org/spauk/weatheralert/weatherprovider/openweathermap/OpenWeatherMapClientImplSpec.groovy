package org.spauk.weatheralert.weatherprovider.openweathermap

import org.spauk.weatheralert.weatherprovider.openweathermap.model.OpenWeatherMapForecast
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class OpenWeatherMapClientImplSpec extends Specification {

    def restTemplate = Mock(RestTemplate)

    def applicationId = "12345"

    def client = new OpenWeatherMapHttpImpl(restTemplate, applicationId)

    def "should call rest template with correct URL and return the result"() {
        given:
        def location = "location"
        def expectedUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&units=metric&appid=" + applicationId
        def expectedForecast = Mock(OpenWeatherMapForecast)

        and: "rest template returns expected forecast when is called with expected url"
        restTemplate.getForObject(expectedUrl, OpenWeatherMapForecast.class) >> expectedForecast

        when:
        def actualForecast = client.getForecastForLocation(location)

        then:
        actualForecast == expectedForecast
    }
}
