package com.nsoft.offer.kafka.producer;

import com.nsoft.offer.kafka.config.KafkaConfig;
import com.nsoft.offer.model.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventProducer {

    private final KafkaTemplate<String, EventDto> eventKafkaTemplate;

    public EventProducer(KafkaTemplate<String, EventDto> eventKafkaTemplate) {
        this.eventKafkaTemplate = eventKafkaTemplate;
    }

    public void sendEvent(String key, EventDto eventDto) {
        log.info("[{}] Pushing new event to event topic: \n{}", this.getClass().getSimpleName(), eventDto);
        eventKafkaTemplate.send(KafkaConfig.EVENT_TOPIC, key, eventDto);
    }
}
