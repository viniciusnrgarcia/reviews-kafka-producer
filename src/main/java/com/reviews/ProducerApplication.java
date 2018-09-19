package com.reviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.reviews.config.KafkaProducerConfiguration;

/**
 * @author Vinicius Garcia
 *
 */
@SpringBootApplication
@ComponentScan(basePackageClasses = { ProducerApplication.class, KafkaProducerConfiguration.class })
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
