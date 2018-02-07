package org.spauk.weatheralert.alert.rest;

import org.spauk.weatheralert.alert.AlertRepository;
import org.spauk.weatheralert.alert.model.AlertSummary;
import org.spauk.weatheralert.common.exception.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = "alert", description = "Operations about weather alerts")
@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertRestController {

    private final AlertRepository alertRepository;

    @ApiOperation("Finds the latest alert summary")
    @ApiResponses(value = { @ApiResponse(code = 404, message = "No alert summaries found") })
    @RequestMapping(path = "/summary/latest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public AlertSummary getLatestAlertSummary() {
        return alertRepository.getLatestAlertSummary()
                              .orElseThrow(() -> new NotFoundException("No alert summaries found"));
    }
}