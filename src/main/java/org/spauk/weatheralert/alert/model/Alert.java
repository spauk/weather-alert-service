package org.spauk.weatheralert.alert.model;

import org.spauk.weatheralert.alertsettings.model.AlertTriggers;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Alert {

    private final String location;

    private final AlertTriggers alertTriggers;

    private final List<DataPoint> dataPoints;

    @Data
    @Builder
    public static class DataPoint {

        private final Instant timestamp;

        private final double temperature;

        private final boolean minTemperatureExceeded;

        private final boolean maxTemperatureExceeded;
    }
}