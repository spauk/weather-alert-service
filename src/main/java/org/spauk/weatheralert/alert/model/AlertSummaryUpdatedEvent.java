package org.spauk.weatheralert.alert.model;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertSummaryUpdatedEvent {

    private final Set<Alert> latestAlertSummary;
}
