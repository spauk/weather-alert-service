package org.spauk.weatheralert.alertsettings;

import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.spauk.weatheralert.alertsettings.model.AlertTriggers;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DummyAlertSettingsRepository implements AlertSettingsRepository {

    @Override
    public Set<AlertSettings> getAll() {
        return Stream.of(AlertSettings.builder()
                                      .location("xcvx")
                                      .alertTriggers(AlertTriggers.builder()
                                                                  .maxTemperature(-2)
                                                                  .maxTemperature(5)
                                                                  .build())
                                      .build(),

                         AlertSettings.builder()
                                      .location("espoo")
                                      .alertTriggers(AlertTriggers.builder()
                                                                  .maxTemperature(-2)
                                                                  .maxTemperature(5)
                                                                  .build())
                                      .build())

                     .collect(Collectors.toSet());
    }
}
