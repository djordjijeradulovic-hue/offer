package com.nsoft.offer.api;

import com.nsoft.offer.api.spec.EventsApi;
import com.nsoft.offer.kafka.producer.EventProducer;
import com.nsoft.offer.model.dto.EventDto;
import com.nsoft.offer.service.IEventService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class EventsApiImpl implements EventsApi {

    @Autowired
    private IEventService eventService;

    @Autowired
    EventProducer eventProducer;

    @Override
    public ResponseEntity<List<EventDto>> getEvents(@RequestParam(required = false, name = "from") Date from) {
        log.info("[{}] Get events", this.getClass().getSimpleName());
        var response = eventService.getEvents(from);
        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<EventDto> createEvent(EventDto dto) {
        log.info("[{}] Create event", this.getClass().getSimpleName());
        var response = eventService.saveEvent(dto);
        eventProducer.sendEvent(response.getId(), response);
        return ResponseEntity.ok(response);
    }
}
