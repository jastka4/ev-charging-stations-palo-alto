package org.jastka4.javaapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jastka4.javaapi.models.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("event")
public class EventController {

    @Value("${resource.url}")
    private String resourceUrl;

    private final RestTemplate restTemplate;

    public EventController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Event[]> getLatestEvents(final Integer number, final String station) {
        log.debug("Received GET call on /event. And will call {}/event!", resourceUrl);

        final Event[] event = restTemplate.getForObject(resourceUrl + "/event?n={number}&station={station}",
                Event[].class, number, station);

        log.debug("Received response from {}: {}", resourceUrl, event);
        return ResponseEntity.ok(event);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addEvent(@RequestBody final Event event) {
        log.debug("Received POST call on /event. And will call {}/event!", resourceUrl);

        try {
            final Event newEvent = restTemplate.postForObject(resourceUrl + "/event", event, Event.class);
            log.debug("Received response from {}: {}", resourceUrl, newEvent);

            return ResponseEntity.ok(newEvent);
        } catch (final HttpClientErrorException e) {
            final String body = e.getResponseBodyAsString();
            log.debug("Received error from {}: {}", resourceUrl, body);

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(body);
        }
    }

    @GetMapping(value = "/consumption", produces = "application/json")
    public ResponseEntity<Event[]> getEventByUserAndEnergyConsumptionRange(final Integer userId, final Integer start,
                                                                           final Integer end) {
        log.debug("Received GET call on /event/consumption. And will call {}/event/consumption!", resourceUrl);

        final Event[] event =
                restTemplate.getForObject(resourceUrl + "/event/consumption?user={userId}&start={start}&end={end}",
                        Event[].class, userId, start, end);

        log.debug("Received response from {}: {}", resourceUrl, event);
        return ResponseEntity.ok(event);
    }
}

