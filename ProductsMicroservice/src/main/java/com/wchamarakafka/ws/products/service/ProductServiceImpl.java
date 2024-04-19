package com.wchamarakafka.ws.products.service;

import com.wchamarakafka.ws.products.rest.CreateProductResModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductResModel createProductResModel) {

        String productId = UUID.randomUUID().toString();
//        TODO: Implement the logic to save the product in the database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                createProductResModel.getTitle(), createProductResModel.getPrice(),
                createProductResModel.getQuantity());

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);

        future.whenComplete((sendResult, throwable) -> {
            if (throwable == null) {
                logger.info("************ Product created event sent successfully: {}", sendResult.getProducerRecord().value());
                logger.info("************ Product MetaData: {}", sendResult.getRecordMetadata());
            } else {
                logger.error("************ Error sending product created event: {}", throwable.getMessage());
            }
        });
        logger.info("************ Product created event sent: {}", productId);
        return productId;
    }
}
