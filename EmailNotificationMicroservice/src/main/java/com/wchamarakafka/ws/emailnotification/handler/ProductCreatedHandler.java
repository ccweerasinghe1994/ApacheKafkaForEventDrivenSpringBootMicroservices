package com.wchamarakafka.ws.emailnotification.handler;

import com.wchamarakafka.ws.core.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "product-created-events-topic")
public class ProductCreatedHandler {

    private final Logger logger = LoggerFactory.getLogger(ProductCreatedHandler.class);

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent) {
        logger.info("Product created event received: {}", productCreatedEvent.getTitle());
    }
}
