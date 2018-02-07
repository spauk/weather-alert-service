package org.spauk.weatheralert.weatherprovider.openweathermap;

import org.spauk.weatheralert.weatherprovider.model.Forecast;
import org.spauk.weatheralert.weatherprovider.openweathermap.model.OpenWeatherMapForecast;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ForecastConverterImpl implements ForecastConverter {

    @Override
    public Forecast convertToCanonicalForecast(OpenWeatherMapForecast nativeForecast) {
        return Forecast.builder()
                       .location(nativeForecast.getCity()
                                               .getName())
                       .dataPoints(nativeForecast.getList()
                                                 .stream()
                                                 .map(this::convertToCanonicalDataPoint)
                                                 .collect(Collectors.toList()))
                       .build();

    }

    private Forecast.DataPoint convertToCanonicalDataPoint(OpenWeatherMapForecast.ListElement nativeDataPoint) {



        return Forecast.DataPoint.builder()
                                 .timestamp(nativeDataPoint.getDt())
                                 .temperature(nativeDataPoint.getMain()
                                                             .getTemp())
                                 .build();
    }
}
