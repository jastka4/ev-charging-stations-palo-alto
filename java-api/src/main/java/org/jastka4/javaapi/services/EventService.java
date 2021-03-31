package org.jastka4.javaapi.services;

import lombok.extern.slf4j.Slf4j;
import org.jastka4.javaapi.models.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@CacheConfig(cacheNames = {"event"})
public class EventService {

    private static final String LOGGING_CALL = "Calling {}";
    private static final String LOGGING_RESPONSE = "Execution time: {} ms\nReceived response from {}: {}";

    @Value("${resource.base-url}")
    private String resourceUrl;

    private final RestTemplate restTemplate;

    public EventService(final RestTemplateBuilder restTemplateBuilder) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        this.restTemplate = restTemplateBuilder.uriTemplateHandler(factory).build();
    }

    @Cacheable
    public Event[] getLatestEvents(final int number, final String station) {
        final URI endpoint = UriComponentsBuilder
                .fromHttpUrl(resourceUrl + "/event")
                .queryParam("n", number)
                .queryParam("station", station)
                .build().toUri();

        log.info(LOGGING_CALL, endpoint);
        long startTime = System.currentTimeMillis();

        final Event[] events = restTemplate.getForObject(endpoint, Event[].class);

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.debug(LOGGING_RESPONSE, elapsedTime, endpoint, events);

        return events;
    }

    @Cacheable
    public Event[] getEventByUserAndEnergyConsumptionRange(final int userId, final int start, final int end) {
        final URI endpoint = UriComponentsBuilder
                .fromHttpUrl(resourceUrl + "/event/consumption")
                .queryParam("user", userId)
                .queryParam("start", start)
                .queryParam("end", end)
                .build().toUri();

        log.info(LOGGING_CALL, endpoint);
        long startTime = System.currentTimeMillis();

        final Event[] events = restTemplate.getForObject(endpoint, Event[].class);

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.debug(LOGGING_RESPONSE, elapsedTime, endpoint, events);

        return events;
    }

    public Object addEvent(final Event event) {
        final URI endpoint = UriComponentsBuilder
                .fromHttpUrl(resourceUrl + "/event/consumption")
                .build().toUri();

        log.info(LOGGING_CALL, endpoint);
        long startTime = System.currentTimeMillis();

        final Event newEvent = restTemplate.postForObject(resourceUrl + "/event", event, Event.class);

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.debug(LOGGING_RESPONSE, elapsedTime, endpoint, newEvent);

        return newEvent;
    }
}
