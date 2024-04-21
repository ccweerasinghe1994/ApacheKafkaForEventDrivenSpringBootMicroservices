package com.wchamarakafka.ws.products.service;

import com.wchamarakafka.ws.core.ProductCreatedEvent;
import com.wchamarakafka.ws.products.rest.CreateProductResModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductResModel createProductResModel) throws ExecutionException, InterruptedException {

        String productId = UUID.randomUUID().toString();
//        TODO: Implement the logic to save the product in the database

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                createProductResModel.getTitle(), createProductResModel.getPrice(),
                createProductResModel.getQuantity());

        logger.info("🚀************ Before sending the event to Kafka ************");
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();
        logger.info("🚀************ After sending the event to Kafka ************");
        logger.info("🚀Sent the event to Kafka with key: {}", result.getProducerRecord().key());
        logger.info("🚀Sent the event to Kafka with offset: {}", result.getRecordMetadata().offset());
        logger.info("🚀Sent the event to Kafka with partition: {}", result.getRecordMetadata().partition());
        logger.info("🚀Sent the event to Kafka with topic: {}", result.getRecordMetadata().topic());


        return productId;
    }
}
