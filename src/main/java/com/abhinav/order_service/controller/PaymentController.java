package com.abhinav.order_service.controller;

import com.abhinav.order_service.dto.PaymentRequest;
import com.abhinav.order_service.domain.Payment;
import com.abhinav.order_service.service.PaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(
            @RequestBody PaymentRequest request) {

        Payment payment = paymentService.processPayment(
                request.getOrderId(),
                request.getAmount());

        return ResponseEntity.ok(payment);
    }
}