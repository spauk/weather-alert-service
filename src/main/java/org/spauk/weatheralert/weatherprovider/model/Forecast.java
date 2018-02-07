package org.spauk.weatheralert.weatherprovider.model;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Forecast {

    private final String location;

    private final List<DataPoint> dataPoints;

    @Data
    @Builder
    public static class DataPoint {

        private final Instant timestamp;

        private final double temperature;
    }
}
