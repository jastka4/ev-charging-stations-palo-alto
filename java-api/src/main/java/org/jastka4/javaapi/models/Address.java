package org.jastka4.javaapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private String address;

    private String city;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("state_province")
    private String stateOrProvince;
}
