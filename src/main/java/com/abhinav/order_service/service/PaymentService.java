package com.abhinav.order_service.service;

import com.abhinav.order_service.domain.Order;
import com.abhinav.order_service.domain.OrderStatus;
import com.abhinav.order_service.domain.Payment;
import com.abhinav.order_service.repository.OrderRepository;
import com.abhinav.order_service.repository.PaymentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Payment processPayment(Long orderId, Double amount) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        Payment payment =
                new Payment(orderId, amount, "SUCCESS");

        paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);

        return payment;
    }
}