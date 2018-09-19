/**
 * 
 */
package com.reviews.usecase;

import java.math.BigDecimal;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reviews.domain.Review;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vinicius
 *
 */
@Slf4j
@Service
public class ReviewService {

    // /**
    // * {@link Environment}
    // */
    // @Autowired
    // private transient Environment env;

    /**
     * Servidor kafka
     */
    @Value("${topic}")
    private transient String topic;

    private transient volatile Long count;

    /**
     * {@link KafkaTemplate}
     */
    @Autowired
    public KafkaTemplate<String, Review> kafkaTemplate;

    public void sendMessageWithCallback(final int totalElement) throws JsonProcessingException {
        if (this.count == null) {
            this.count = 0L;
        }

        Review review = new Review();
        review.setNuOperation(Long.MAX_VALUE);
        review.setUser("userRecordCreation");
        review.setNetValue(BigDecimal.TEN);
        review.setTotalMessages(totalElement);

        for (long i = 0; i < totalElement; i++) {
            review.setCountMessages(this.count++);
            review.setSequenceMessage(i);

            ListenableFuture<SendResult<String, Review>> future = this.kafkaTemplate
                    .send(new ProducerRecord<String, Review>(this.topic, review));

            future.addCallback(new ListenableFutureCallback<SendResult<String, Review>>() {

                @Override
                public void onSuccess(SendResult<String, Review> message) {
                    log.debug("Mensage enviada com sucesso! " + message.getProducerRecord());
                }

                @Override
                public void onFailure(Throwable message) {
                    log.error("Erro ao enviar mensagem!" + message.getStackTrace());
                }
            });

        }
    }

}
