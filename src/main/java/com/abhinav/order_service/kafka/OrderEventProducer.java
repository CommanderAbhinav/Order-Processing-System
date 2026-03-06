package com.abhinav.order_service.kafka;

import com.abhinav.order_service.events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderCreatedEvent(OrderCreatedEvent event){
        String message = event.getOrderId() +"," + event.getUserId() + "," + event.getAmount();
        kafkaTemplate.send("order-created",message);
    }
}
