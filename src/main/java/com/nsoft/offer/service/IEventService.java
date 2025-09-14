package com.nsoft.offer.service;

import com.nsoft.offer.model.dto.EventDto;

import java.util.Date;
import java.util.List;

public interface IEventService {

    List<EventDto> getEvents(Date from);

    EventDto saveEvent(EventDto dto);

    void saveEvents(List<EventDto> eventDTOs);

    void deleteEventById(String id);

    List<String> getExpiredEventsIds();

}
