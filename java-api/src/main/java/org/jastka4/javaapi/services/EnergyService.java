package org.jastka4.javaapi.services;

import org.jastka4.javaapi.models.EnergyConsumption;
import org.jastka4.javaapi.models.EnergyConsumptionDetails;

import java.time.LocalDate;

public interface EnergyService {
    EnergyConsumptionDetails getConsumptionDetails();

    EnergyConsumption[] getConsumptionByDateRange(LocalDate start, LocalDate end);
}
