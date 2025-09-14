package com.nsoft.offer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.nsoft.offer.model.dto.EventDto;
import com.nsoft.offer.model.dto.MarketDto;
import com.nsoft.offer.model.entity.*;
import com.nsoft.offer.service.IEventService;
import com.nsoft.offer.service.IMarketService;
import com.nsoft.offer.ws.notification.NotificationService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class DataLoaderServiceImpl {

    @Autowired
    IEventService eventService;

    @Autowired
    IMarketService marketService;

    @Autowired
    NotificationService notificationService;

    private final JsonMapper mapper = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    @PostConstruct
    @Transactional
    public void loadData() {
        log.info("[{}] Loading initial data", this.getClass().getSimpleName());
        try {
            var marketList = mapper.readValue(new File("src/main/resources/initial-data/markets.json"), new TypeReference<List<MarketDto>>() {});
            marketService.saveMarkets(marketList);

            var eventList = mapper.readValue(new File("src/main/resources/initial-data/events.json"), new TypeReference<List<EventDto>>() {});
            eventService.saveEvents(eventList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load initial data", e);
        }
    }

    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void deleteExpiredEvents() {
        log.info("[{}] Removing expired events", this.getClass().getSimpleName());
        var ids = eventService.getExpiredEventsIds();
        ids.forEach(id -> {
            eventService.deleteEventById(id);
            try {
                notificationService.sendEventDeletedNotification(id);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            log.info("[{}] Removed event with id: {}", this.getClass().getSimpleName(), id);
        });
    }

}
