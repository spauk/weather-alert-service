package org.spauk.weatheralert.alertsettings;

import org.spauk.weatheralert.alertsettings.model.AlertSettings;

import java.util.Set;

/**
 * Weather alert settings storage.
 */
public interface AlertSettingsRepository {

    /**
     * Retrieves alert settings for all the set locations.
     *
     * @return non-empty collection if any alert settings are set, otherwise empty collection
     */
    Set<AlertSettings> getAllSettings();
}