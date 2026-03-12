package com.abhinav.order_service.repository;

import com.abhinav.order_service.domain.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventsRepository extends JpaRepository<ProcessedEvent, String> {
    boolean existsByOrderId(Long orderId);
}
