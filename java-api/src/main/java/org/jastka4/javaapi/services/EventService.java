package org.jastka4.javaapi.services;

import org.jastka4.javaapi.models.Event;

public interface EventService {
    Event addEvent(Event event);

    Event[] getLatestEvents(int number, String station);

    Event[] getEventByUserAndEnergyConsumptionRange(int user, int start, int end);
}
