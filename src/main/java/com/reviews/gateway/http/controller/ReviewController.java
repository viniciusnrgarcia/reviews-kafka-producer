/**
 * 
 */
package com.reviews.gateway.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reviews.usecase.ReviewService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vinicius
 *
 */
@Slf4j
@RestController
public class ReviewController {

    @Autowired
    private transient ReviewService service;

    @GetMapping(value = "/reviews/{totalElement}")
    public ResponseEntity<String> publishReviews(@PathVariable int totalElement) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("PublishReviews");

        try {
            this.service.sendMessageWithCallback(totalElement);

        } catch (JsonProcessingException e) {
            log.error("Erro enviar mensagem para servidor ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        stopWatch.stop();
        log.info("Total time in milliseconds: {}", stopWatch.getLastTaskTimeMillis());
        log.info("Total time in seconds: {}", stopWatch.getTotalTimeSeconds());

        return ResponseEntity.ok("Reviews publicadas!");
    }

}
