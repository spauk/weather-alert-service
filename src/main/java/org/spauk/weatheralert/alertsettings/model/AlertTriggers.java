package org.spauk.weatheralert.alertsettings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertTriggers {

    private double minTemperature;

    private double maxTemperature;
}
