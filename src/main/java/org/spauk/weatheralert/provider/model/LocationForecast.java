package org.spauk.weatheralert.provider.model;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LocationForecast {

    private final String location;

    private final List<DataPoint> dataPoints;

    @Value
    @Builder
    public static class DataPoint {

        private final Instant timestamp;

        private final double temperature;
    }
}
