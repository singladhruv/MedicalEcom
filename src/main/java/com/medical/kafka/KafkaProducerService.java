package com.medical.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.medical.kafka.events.OrderEvent;
import com.medical.kafka.events.ProductEvent;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductEvent(ProductEvent event) {
        kafkaTemplate.send("product-events", event);
    }

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send("order-events", event);
    }
}
