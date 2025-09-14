package com.nsoft.offer.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsoft.offer.model.dto.MarketDto;
import com.nsoft.offer.ws.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketConsumer {

    @Autowired
    NotificationService notificationService;

    @KafkaListener(
            topics = "market",
            groupId = "offer-group",
            containerFactory = "marketKafkaListenerContainerFactory")
    public void handleMarketCreation(MarketDto market) throws JsonProcessingException {
        log.info("[{}] Market received: \n{}\n Sending notification", this.getClass().getSimpleName(), market);
        notificationService.sendMarketCreatedNotification(market);
    }

}
