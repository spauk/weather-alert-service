package org.spauk.weatheralert.alert;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.spauk.weatheralert.alert.model.AlertSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AlertLogger {

    private final ObjectMapper objectMapper;

    private final String filePath;

    public AlertLogger(@Autowired ObjectMapper objectMapper,
                       @Value("${service.alert-summary-log-file}") String filePath) {
        this.objectMapper = objectMapper;
        this.filePath = filePath;
    }

    @EventListener
    @Async
    public void onEvent(AlertSummary event) {

        LOGGER.info("Received alert summary event: {}", event);

        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(new File(filePath), event);
        } catch (Exception e) {
            LOGGER.error("Failed to write alert summary log file", e);
        }
    }
}
