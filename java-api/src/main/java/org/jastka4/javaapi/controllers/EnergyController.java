package org.jastka4.javaapi.controllers;

import org.jastka4.javaapi.models.EnergyConsumption;
import org.jastka4.javaapi.models.EnergyConsumptionDetails;
import org.jastka4.javaapi.services.EnergyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;

@RestController
@RequestMapping("energy")
public class EnergyController {

    @Resource
    private EnergyService energyService;

    @GetMapping(value = "/consumption-details", produces = "application/json")
    public ResponseEntity<EnergyConsumptionDetails> getConsumptionDetails() {
        return ResponseEntity.ok(energyService.getConsumptionDetails());
    }

    @GetMapping(value = "/consumption", produces = "application/json")
    public ResponseEntity<EnergyConsumption[]> getConsumptionByDateRange(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate end) {
        return ResponseEntity.ok(energyService.getConsumptionByDateRange(start, end));
    }
}

