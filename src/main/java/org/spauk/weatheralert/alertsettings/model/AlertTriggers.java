package org.spauk.weatheralert.alertsettings.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertTriggers {

    private final double minTemperature;

    private final double maxTemperature;
}
