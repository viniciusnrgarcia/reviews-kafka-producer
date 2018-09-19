/**
 *
 */
package com.reviews.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.reviews.domain.Review;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vnrg
 *
 */
@Slf4j
@Configuration
public class KafkaProducerConfiguration {

    /**
     * Servidor kafka
     */
    @Value("${spring.kafka.bootstrap.servers}")
    private transient String bootstrapServers;

    /**
     * @see https://kafka.apache.org/documentation/#producerconfigs for more
     *      properties.
     * @return
     */
    @Bean
    public ProducerFactory<String, Review> producerFactory() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "ReviewsKafkaProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        return new DefaultKafkaProducerFactory<String, Review>(properties);
    }

    /**
     * @return {@link KafkaTemplate}
     */
    @Bean
    public KafkaTemplate<String, Review> kafkaTemplate() {
        log.info("Criando KafkaTemplate");
        return new KafkaTemplate<String, Review>(this.producerFactory());
    }

}
