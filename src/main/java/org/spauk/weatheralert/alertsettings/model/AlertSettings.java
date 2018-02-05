package org.spauk.weatheralert.alertsettings.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertSettings {

    private final String location;

    private final AlertTriggers alertTriggers;
}