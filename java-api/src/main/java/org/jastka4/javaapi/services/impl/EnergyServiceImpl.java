package org.jastka4.javaapi.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.jastka4.javaapi.models.EnergyConsumption;
import org.jastka4.javaapi.models.EnergyConsumptionDetails;
import org.jastka4.javaapi.services.EnergyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Slf4j
@Service("energyService")
@CacheConfig(cacheNames = {"energy"})
public class EnergyServiceImpl implements EnergyService {

    private static final String LOGGING_CALL = "Calling {}";
    private static final String LOGGING_RESPONSE = "Execution time: {} ms\nReceived response from {}: {}";

    @Value("${resource.base-url}")
    private String resourceUrl;

    private final RestTemplate restTemplate;

    public EnergyServiceImpl(final RestTemplateBuilder restTemplateBuilder) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        this.restTemplate = restTemplateBuilder.uriTemplateHandler(factory).build();
    }

    @Cacheable
    public EnergyConsumptionDetails getConsumptionDetails() {
        final URI endpoint = UriComponentsBuilder.fromHttpUrl(resourceUrl + "/energy/consumption-details").build().toUri();

        log.info(LOGGING_CALL, endpoint);
        long startTime = System.currentTimeMillis();

        final EnergyConsumptionDetails energyConsumptionDetails = restTemplate.getForObject(endpoint, EnergyConsumptionDetails.class);

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.debug(LOGGING_RESPONSE, elapsedTime, endpoint, energyConsumptionDetails);

        return energyConsumptionDetails;
    }

    @Cacheable
    public EnergyConsumption[] getConsumptionByDateRange(final LocalDate start, final LocalDate end) {
        final URI endpoint = UriComponentsBuilder
                .fromHttpUrl(resourceUrl + "/energy/consumption")
                .queryParam("start", start)
                .queryParam("end", end)
                .build().toUri();

        log.info(LOGGING_CALL, endpoint);
        long startTime = System.currentTimeMillis();

        final EnergyConsumption[] energyConsumptions = restTemplate.getForObject(endpoint, EnergyConsumption[].class);

        long elapsedTime = System.currentTimeMillis() - startTime;
        log.debug(LOGGING_RESPONSE, elapsedTime, endpoint, energyConsumptions);

        return energyConsumptions;
    }
}
