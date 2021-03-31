package org.jastka4.javaapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Port implements Serializable {

    @JsonProperty("plug_type")
    private String plugType;

    @JsonProperty("port_number")
    private String portNumber;

    @JsonProperty("port_type")
    private String portType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("station")
    private Station station;
}
