package org.spauk.weatheralert.alert.model;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertSummary {

    private final Instant timestamp;

    private final Set<Alert> alerts;
}
