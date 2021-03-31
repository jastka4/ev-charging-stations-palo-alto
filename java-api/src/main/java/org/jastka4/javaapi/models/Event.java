package org.jastka4.javaapi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Event {

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonProperty("charging_time")
    private LocalTime chargingTime;

    private String currency;

    @JsonProperty("driver_postal_code")
    private String driverPostalCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonProperty("end_time_zone")
    private String endTimeZone;

    @JsonProperty("ended_by")
    private String endedBy;

    private Double energy;

    private Double fee;

    @JsonProperty("gasoline_savings")
    private Double gasolineSavings;

    @JsonProperty("ghg_savings")
    private Double ghgSavings;

    @JsonProperty("plug_in_event_id")
    private Long plugInEventId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Port port;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("start_time_zone")
    private String startTimeZone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonProperty("total_duration")
    private LocalTime totalDuration;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonProperty("transaction_date")
    private LocalDateTime transactionDate;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("station_name")
    private String stationName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("port_number")
    private String portNumber;
}