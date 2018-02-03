package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.AlertSettings;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface AlertSettingsRepository {

    CompletableFuture<Set<AlertSettings>> findAll();
}