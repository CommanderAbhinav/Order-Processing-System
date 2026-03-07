package com.abhinav.order_service.repository;

import com.abhinav.order_service.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {
    boolean existsByOrderId(Long orderId);
}