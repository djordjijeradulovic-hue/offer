package com.nsoft.offer.service.impl;

import com.nsoft.offer.helper.DateHelper;
import com.nsoft.offer.model.dto.*;
import com.nsoft.offer.model.entity.*;
import com.nsoft.offer.model.enums.*;
import com.nsoft.offer.repository.EventRepository;
import com.nsoft.offer.repository.MarketRepository;
import com.nsoft.offer.service.IEventService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EventServiceImpl implements IEventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    MarketRepository marketRepository;

    @Override
    public List<EventDto> getEvents(Date from) {
        log.info("[{}] Getting events", this.getClass().getSimpleName());
        var events = eventRepository.findByStartsAtAfter(DateHelper.getValidDate(from));
        log.info("[{}] Filtering valid events", this.getClass().getSimpleName());
        return events.stream()
                .filter(e -> e.getStatus() == EventStatus.ACTIVE)
                .peek(e -> e.setMarkets(
                        e.getMarkets().stream()
                                .filter(em -> em.getStatus() == EventMarketStatus.ACTIVE)
                                .filter(em -> em.getMarket() != null && em.getMarket().getStatus() == MarketStatus.ACTIVE)
                                .peek(em -> em.setOutcomes(
                                        em.getOutcomes().stream()
                                                .filter(emo -> emo.getStatus() == EventMarketOutcomeStatus.ACTIVE)
                                                .filter(emo -> emo.getOutcome() != null && emo.getOutcome().getStatus() == MarketOutcomeStatus.ACTIVE)
                                                .toList()
                                ))
                                .toList()
                )).map(this::toEventDto).toList();
    }

    @Override
    @Transactional
    public EventDto saveEvent(EventDto dto) {
        log.info("[{}] Saving event with id: {}", this.getClass().getSimpleName(), dto.getId());
        var event = toEventEntity(dto);
        var saved = eventRepository.save(event);
        return toEventDto(saved);
    }

    @Transactional
    public void saveEvents(List<EventDto> eventDTOs) {
        log.info("[{}] Saving events", this.getClass().getSimpleName());
        for (EventDto eventDTO : eventDTOs) {
            Event event = toEventEntity(eventDTO);
            eventRepository.save(event);
        }
    }

    @Override
    public void deleteEventById(String id) {
        log.info("[{}] Deleting event by id : {}", this.getClass().getSimpleName(), id);
        eventRepository.deleteById(id);
    }

    @Override
    public List<String> getExpiredEventsIds() {
        log.info("[{}] Getting expired events ids", this.getClass().getSimpleName());
        return eventRepository.findEventIdsOlderThanCurrentTime();
    }

    private Event toEventEntity(EventDto dto) {
        var event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setStartsAt(dto.getStartsAt());
        event.setStatus(EventStatus.fromValue(dto.getStatus().getValue()));

        List<EventMarket> eventMarkets = new ArrayList<>();
        for (EventMarketDto marketDTO : dto.getMarkets()) {
            var eventMarket = toEventMarketEntity(marketDTO);
            if (eventMarket != null) {
                eventMarkets.add(eventMarket);
            }
        }
        event.setMarkets(eventMarkets);

        return event;
    }

    private EventMarket toEventMarketEntity(EventMarketDto dto) {
        var market = marketRepository.findById(dto.getMarketId());
        if (market.isPresent()) {
            var eventMarket = new EventMarket();
            eventMarket.setId(dto.getId());
            eventMarket.setMarket(market.get());
            eventMarket.setStatus(EventMarketStatus.fromValue(dto.getStatus().getValue()));

            List<EventMarketOutcome> outcomes = new ArrayList<>();
            for (EventMarketOutcomeDto outcomeDTO : dto.getOutcomes()) {
                var eventMarketOutcome = toEventMarketOutcomeEntity(outcomeDTO);
                var outcome = market.get().getOutcomes().stream().filter(o -> o.getId().equals(outcomeDTO.getOutcomeId())).findFirst();
                if (outcome.isPresent()) {
                    eventMarketOutcome.setOutcome(outcome.get());
                    outcomes.add(eventMarketOutcome);
                }
            }
            eventMarket.setOutcomes(outcomes);
            return eventMarket;
        }
        return null;
    }

    private EventMarketOutcome toEventMarketOutcomeEntity(EventMarketOutcomeDto dto) {
        var outcome = new EventMarketOutcome();
        outcome.setId(dto.getId());
        outcome.setOdd(dto.getOdds());
        outcome.setStatus(EventMarketOutcomeStatus.fromValue(dto.getStatus().getValue()));
        return outcome;
    }

    private EventDto toEventDto(Event event) {
        var dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setStartsAt(event.getStartsAt());
        dto.setStatus(EventDto.StatusEnum.fromValue(event.getStatus().getValue()));
        dto.setMarkets(event.getMarkets().stream().map(this::toEventMarketDto).toList());
        return dto;
    }

    private EventMarketDto toEventMarketDto(EventMarket market) {
        var dto = new EventMarketDto();
        dto.setId(market.getId());
        dto.setMarketId(market.getMarket().getId());
        dto.setStatus(EventMarketDto.StatusEnum.fromValue(market.getStatus().getValue()));
        dto.setOutcomes(market.getOutcomes().stream().map(this::toEventMarketOutcomeDto).toList());
        return dto;
    }

    private EventMarketOutcomeDto toEventMarketOutcomeDto(EventMarketOutcome outcome) {
        var dto = new EventMarketOutcomeDto();
        dto.setId(outcome.getId());
        dto.setOutcomeId(outcome.getOutcome().getId());
        dto.setStatus(EventMarketOutcomeDto.StatusEnum.fromValue(outcome.getStatus().getValue()));
        dto.setOdds(outcome.getOdd());
        return dto;
    }

}
