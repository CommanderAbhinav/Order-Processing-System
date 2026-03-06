package com.abhinav.order_service.kafka;


import com.abhinav.order_service.domain.Payment;
import com.abhinav.order_service.repository.PaymentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventConsumer {
    private final PaymentRepository paymentRepository;

    public PaymentEventConsumer(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consume(String message) {

        String[] parts = message.split(",");

        Long orderId = Long.parseLong(parts[0]);
        Double amount = Double.parseDouble(parts[2]);

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus("SUCCESS");

        paymentRepository.save(payment);

        System.out.println("Payment created for order " + orderId);
    }
}
