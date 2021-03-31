package org.jastka4.javaapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Station {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Address address;

    private Double latitude;

    private Double longitude;

    @JsonProperty("mac_address")
    private String macAddress;

    @JsonProperty("org_name")
    private String orgName;

    @JsonProperty("station_name")
    private String stationName;
}
