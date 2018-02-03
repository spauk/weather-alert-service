package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.model.Forecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapForecast;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConverterImpl implements Converter {

    private static Function<OpenWeatherMapForecast.ListElement, Forecast.DataPoint> NATIVE_DATA_POINT_TO_CANONICAL =
                    (nativeDataPoint) -> Forecast.DataPoint.builder()
                                                           .timestamp(nativeDataPoint.getDt())
                                                           .temperature(nativeDataPoint.getMain()
                                                                                               .getTemp())
                                                           .build();

    private static Function<OpenWeatherMapForecast, Forecast> NATIVE_FORECAST_TO_CANONICAL =
                    (nativeForecast) -> Forecast.builder()
                                                .location(nativeForecast.getCity()
                                                                                .getName())
                                                .dataPoints(nativeForecast.getList()
                                                                                  .stream()
                                                                                  .map(NATIVE_DATA_POINT_TO_CANONICAL)
                                                                                  .collect(Collectors.toList()))
                                                .build();

    @Override
    public Function<OpenWeatherMapForecast, Forecast> nativeForecastToCanonical() {
        return NATIVE_FORECAST_TO_CANONICAL;
    }
}
