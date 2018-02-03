package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSettings;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class DummyAlertSettingsRepository implements AlertSettingsRepository {

    @Override
    public Set<AlertSettings> findAll() {
        return Stream.of("espoo", "alicante")
                     .map(location -> AlertSettings.builder()
                                                   .location(location)
                                                   .build())
                     .collect(Collectors.toSet());
    }
}
