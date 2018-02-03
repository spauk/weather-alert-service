package org.spauk.weatheralert.alert.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlertSettings {

    private final String location;

    private final double minTemperature;

    private final double maxTemperature;
}