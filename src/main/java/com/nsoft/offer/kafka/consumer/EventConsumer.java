package com.nsoft.offer.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsoft.offer.model.dto.EventDto;
import com.nsoft.offer.ws.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {

    @Autowired
    NotificationService notificationService;

    @KafkaListener(
            topics = "event",
            groupId = "offer-group",
            containerFactory = "eventKafkaListenerContainerFactory",
            concurrency = "2")
    public void handleEventCreation(EventDto event) throws JsonProcessingException {
        log.info("[{}] Event received: \n{}\n Sending notification", this.getClass().getSimpleName(), event);
        notificationService.sendEventCreatedNotification(event);
    }

}
