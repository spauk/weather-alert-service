package org.spauk.weatheralert.alert.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AlertSettings {

    private final String location;

    private final double minTemperature;

    private final double maxTemperature;
}