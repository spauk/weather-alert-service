package org.spauk.weatheralert.alertsettings;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.spauk.weatheralert.alertsettings.model.AlertSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AlertSettingsRepositoryImpl implements AlertSettingsRepository {

    private final ObjectMapper objectMapper;

    private final ResourceLoader resourceLoader;

    private final String alertSettingsFilePath;

    public AlertSettingsRepositoryImpl(@Autowired ObjectMapper objectMapper,
                                       @Autowired ResourceLoader resourceLoader,
                                       @Value("${service.alert-settings-file}") String alertSettingsFilePath) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.alertSettingsFilePath = alertSettingsFilePath;
    }

    @Override
    public Set<AlertSettings> getAll() {
        Resource template = resourceLoader.getResource(alertSettingsFilePath);
        try {
            return Arrays.stream(objectMapper.readValue(template.getInputStream(), AlertSettings[].class))
                         .collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.error("Failed lo load the repository", e);
        }
        return Collections.emptySet();
    }
}
