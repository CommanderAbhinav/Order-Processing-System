package com.abhinav.order_service.kafka;


import com.abhinav.order_service.domain.Payment;
import com.abhinav.order_service.events.OrderCreatedEvent;
import com.abhinav.order_service.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {
    private final PaymentRepository paymentRepository;
    private ObjectMapper objectMapper;

    public PaymentEventConsumer(PaymentRepository paymentRepository
                            ,ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
    }

    @RetryableTopic(attempts = "3" ,backoff = @Backoff(delay = 2000))
    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consume(String message) {

        try {

            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);

            Long orderId = event.getOrderId();

            if (paymentRepository.existsByOrderId(orderId)) {
                System.out.println("Payment already exists for order " + orderId);
                return;
            }

            Payment payment = new Payment();
            payment.setOrderId(event.getOrderId());
            payment.setAmount(event.getAmount());
            payment.setStatus("SUCCESS");

            paymentRepository.save(payment);

            System.out.println("Payment created for order " + orderId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
