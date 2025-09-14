package com.nsoft.offer.kafka.config;

import com.nsoft.offer.model.dto.EventDto;
import com.nsoft.offer.model.dto.MarketDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String EVENT_TOPIC = "event";
    public static final String MARKET_TOPIC = "market";

    private Map<String, Object> baseConsumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Bean
    public ConsumerFactory<String, EventDto> eventConsumerFactory() {
        JsonDeserializer<EventDto> deserializer = new JsonDeserializer<>(EventDto.class);
        return new DefaultKafkaConsumerFactory<>(
                baseConsumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventDto> eventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EventDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(eventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MarketDto> marketConsumerFactory() {
        JsonDeserializer<MarketDto> deserializer = new JsonDeserializer<>(MarketDto.class);
        return new DefaultKafkaConsumerFactory<>(
                baseConsumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MarketDto> marketKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MarketDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(marketConsumerFactory());
        return factory;
    }

    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, EventDto> eventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, EventDto> eventKafkaTemplate() {
        return new KafkaTemplate<>(eventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, MarketDto> marketProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseProducerProps());
    }

    @Bean
    public KafkaTemplate<String, MarketDto> marketKafkaTemplate() {
        return new KafkaTemplate<>(marketProducerFactory());
    }

    @Bean
    public NewTopic eventTopic() {
        return new NewTopic(EVENT_TOPIC, 2, (short) 1);
    }

    @Bean
    public NewTopic marketTopic() {
        return new NewTopic(MARKET_TOPIC, 1, (short) 1);
    }

}