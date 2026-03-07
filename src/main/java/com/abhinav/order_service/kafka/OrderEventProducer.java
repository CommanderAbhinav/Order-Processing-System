package com.abhinav.order_service.kafka;

import com.abhinav.order_service.events.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                              ObjectMapper objectMapper){
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishOrderCreatedEvent(OrderCreatedEvent event){
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-created", message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish event", e);
        }
    }
}
