package com.medical.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.medical.kafka.events.OrderEvent;
import com.medical.kafka.events.ProductEvent;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "product-events", groupId = "ecommerce-group")
    public void consumeProductEvent(ProductEvent event) {
        System.out.println("Received product event: " + event.getAction() + " for product " + event.getName());
    }

    @KafkaListener(topics = "order-events", groupId = "ecommerce-group")
    public void consumeOrderEvent(OrderEvent event) {
        System.out.println("Received order event: orderId=" + event.getOrderId() + " userId=" + event.getUserId());
    }
}
