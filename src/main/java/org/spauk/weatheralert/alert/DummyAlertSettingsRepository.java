package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSettings;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class DummyAlertSettingsRepository implements AlertSettingsRepository {

    @Override
    public CompletableFuture<Set<AlertSettings>> findAll() {
        return null;
    }
}
