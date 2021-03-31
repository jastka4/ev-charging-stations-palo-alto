package org.jastka4.javaapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jastka4.javaapi.models.Event;
import org.jastka4.javaapi.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("event")
public class EventController {

    @Resource
    private EventService eventService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Event[]> getLatestEvents(final Integer number, final String station) {
        return ResponseEntity.ok(eventService.getLatestEvents(number, station));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addEvent(@RequestBody final Event event) {
        try {
            return ResponseEntity.ok(eventService.addEvent(event));
        } catch (final HttpClientErrorException e) {
            final String body = e.getResponseBodyAsString();
            log.debug("Received error when calling the external API: {}", body);

            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(body);
        }
    }

    @GetMapping(value = "/consumption", produces = "application/json")
    public ResponseEntity<Event[]> getEventByUserAndEnergyConsumptionRange(final Integer user, final Integer start,
                                                                           final Integer end) {
        return ResponseEntity.ok(eventService.getEventByUserAndEnergyConsumptionRange(user, start, end));
    }
}

