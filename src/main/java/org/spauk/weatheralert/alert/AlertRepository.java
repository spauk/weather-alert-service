package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSummary;

import java.util.Optional;

/**
 * Weather alert storage.
 */
public interface AlertRepository {

    /**
     * Saves a weather alert summary
     *
     * @param alertSummary - weather alert summary
     */
    void saveAlertSummary(AlertSummary alertSummary);

    /**
     * Retrieves the latest generated weather alert summary
     *
     * @return non-empty optional containing the latest weather alert summary, or empty optional if no alert summaries are available
     */
    Optional<AlertSummary> getLatestAlertSummary();
}