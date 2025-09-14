package com.nsoft.offer.kafka.producer;

import com.nsoft.offer.kafka.config.KafkaConfig;
import com.nsoft.offer.model.dto.MarketDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MarketProducer {

    private final KafkaTemplate<String, MarketDto> marketKafkaTemplate;

    public MarketProducer(KafkaTemplate<String, MarketDto> marketKafkaTemplate) {
        this.marketKafkaTemplate = marketKafkaTemplate;
    }

    public void sendMarket(String key, MarketDto marketDto) {
        log.info("[{}] Pushing new market to market topic: \n{}", this.getClass().getSimpleName(), marketDto);
        marketKafkaTemplate.send(KafkaConfig.MARKET_TOPIC, key, marketDto);
    }
}
