package org.jastka4.javaapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jastka4.javaapi.models.EnergyConsumption;
import org.jastka4.javaapi.models.EnergyConsumptionDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("energy")
public class EnergyController {

    @Value("${resource.url}")
    private String resourceUrl;

    private final RestTemplate restTemplate;

    public EnergyController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/consumption-details", produces = "application/json")
    public ResponseEntity<EnergyConsumptionDetails> getConsumptionDetails() {
        log.debug("Received GET call on /energy/consumption-details. And will call {}/energy/consumption-details!", resourceUrl);

        final EnergyConsumptionDetails energyConsumptionDetails =
                restTemplate.getForObject(resourceUrl + "/energy/consumption-details", EnergyConsumptionDetails.class);

        log.debug("Received response from {}: {}", resourceUrl, energyConsumptionDetails);
        return ResponseEntity.ok(energyConsumptionDetails);
    }

    @GetMapping(value = "/consumption", produces = "application/json")
    public ResponseEntity<EnergyConsumption[]> getConsumptionByDateRange(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate end) {
        log.debug("Received GET call on /energy/consumption. And will call {}/energy/consumption with params {}, {}!",
                resourceUrl, start, end);

        final EnergyConsumption[] energyConsumption =
                restTemplate.getForObject(resourceUrl + "/energy/consumption?start={start}&end={end}",
                        EnergyConsumption[].class, start, end);

        log.debug("Received response from {}: {}", resourceUrl, energyConsumption);
        return ResponseEntity.ok(energyConsumption);
    }
}

