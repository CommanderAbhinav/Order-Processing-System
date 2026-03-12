package com.abhinav.order_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_event")
public class ProcessedEvent {
    @Id
    private Long eventId;

    private LocalDateTime processedAt;

    public ProcessedEvent(){
    }

    public ProcessedEvent(Long eventId, LocalDateTime processedAt) {
        this.eventId = eventId;
        this.processedAt = processedAt;
    }

    public Long getEventId() {
        return eventId;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
}
