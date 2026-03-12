package com.abhinav.order_service.kafka;


import com.abhinav.order_service.domain.Payment;
import com.abhinav.order_service.domain.ProcessedEvent;
import com.abhinav.order_service.events.OrderCreatedEvent;
import com.abhinav.order_service.repository.PaymentRepository;
import com.abhinav.order_service.repository.ProcessedEventsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentEventConsumer {
    private final PaymentRepository paymentRepository;
    private ObjectMapper objectMapper;
    private final ProcessedEventsRepository processedEventsRepository;

    public PaymentEventConsumer(PaymentRepository paymentRepository
                            ,ObjectMapper objectMapper,ProcessedEventsRepository processedEventsRepository) {
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
        this.processedEventsRepository = processedEventsRepository;
    }

    @RetryableTopic(attempts = "3" ,backoff = @Backoff(delay = 2000))
    @KafkaListener(topics = "order-created", groupId = "order-group")
    @Transactional
    public void consume(String message) {

        try {

            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);

            Long orderId = event.getOrderId();

            if(processedEventsRepository.existsByOrderId(orderId)){
                System.out.println("Duplicate event skipped: " + orderId);
                return;
            }

            if (paymentRepository.existsByOrderId(orderId)) {
                System.out.println("Payment already exists for order " + orderId);
                return;
            }

            Payment payment = new Payment();
            payment.setOrderId(event.getOrderId());
            payment.setAmount(event.getAmount());
            payment.setStatus("SUCCESS");

            paymentRepository.save(payment);

            ProcessedEvent processedEvent = new ProcessedEvent(orderId, LocalDateTime.now());

            System.out.println("Payment created for order " + orderId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "order-created-dlt", groupId = "order-group")
    public void consumeDLT(String message) {

        System.out.println("Message moved to DLT: " + message);

    }
}
