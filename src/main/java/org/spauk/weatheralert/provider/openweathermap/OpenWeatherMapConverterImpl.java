package org.spauk.weatheralert.provider.openweathermap;

import org.spauk.weatheralert.provider.model.LocationForecast;
import org.spauk.weatheralert.provider.openweathermap.model.OpenWeatherMapLocationForecast;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OpenWeatherMapConverterImpl implements OpenWeatherMapConverter {

    private static Function<OpenWeatherMapLocationForecast.ListElement, LocationForecast.DataPoint> NATIVE_DATA_POINT_TO_CANONICAL =
                    (nativeDataPoint) -> LocationForecast.DataPoint.builder()
                                                                   .timestamp(nativeDataPoint.getDt())
                                                                   .temperature(nativeDataPoint.getMain()
                                                                                               .getTemp())
                                                                   .build();

    private static Function<OpenWeatherMapLocationForecast, LocationForecast> NATIVE_LOCATION_FORECAST_TO_CANONICAL =
                    (nativeForecast) -> LocationForecast.builder()
                                                        .location(nativeForecast.getCity()
                                                                                .getName())
                                                        .dataPoints(nativeForecast.getList()
                                                                                  .stream()
                                                                                  .map(NATIVE_DATA_POINT_TO_CANONICAL)
                                                                                  .collect(Collectors.toList()))
                                                        .build();

    @Override
    public Function<OpenWeatherMapLocationForecast, LocationForecast> nativeForecastToCanonical() {
        return NATIVE_LOCATION_FORECAST_TO_CANONICAL;
    }
}
