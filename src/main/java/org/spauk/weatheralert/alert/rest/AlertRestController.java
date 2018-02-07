package org.spauk.weatheralert.alert.rest;

import org.spauk.weatheralert.alert.AlertRepository;
import org.spauk.weatheralert.alert.model.AlertSummary;
import org.spauk.weatheralert.common.exception.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Weather alert REST controller
 */
@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertRestController {

    private final AlertRepository alertRepository;

    /**
     * Retrieves latest weather alert summary
     *
     * @return latest alert summary as JSON if found, otherwise returns 404 Not Found
     */
    @RequestMapping(path = "/summary/latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AlertSummary getLatestAlertSummary() {
        return alertRepository.getLatestAlertSummary()
                              .orElseThrow(() -> new NotFoundException("No alert summaries found"));
    }
}