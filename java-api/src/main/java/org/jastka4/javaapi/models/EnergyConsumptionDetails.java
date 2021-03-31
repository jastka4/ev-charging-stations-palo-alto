package org.jastka4.javaapi.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnergyConsumptionDetails implements Serializable {

    private Double avg;
    private Double sum;
}
