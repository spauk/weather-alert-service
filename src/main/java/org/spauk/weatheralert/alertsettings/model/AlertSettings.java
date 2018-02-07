package org.spauk.weatheralert.alertsettings.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertSettings {

    private String location;

    private AlertTriggers alertTriggers;
}