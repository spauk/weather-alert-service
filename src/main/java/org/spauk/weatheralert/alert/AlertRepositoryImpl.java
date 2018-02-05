package org.spauk.weatheralert.alert;

import org.spauk.weatheralert.alert.model.Alert;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class AlertRepositoryImpl implements AlertRepository {

    @Override
    public void updateSummary(Set<Alert> alerts) {

    }

    @Override
    public Set<Alert> getLatestSummary() {
        return null;
    }
}
