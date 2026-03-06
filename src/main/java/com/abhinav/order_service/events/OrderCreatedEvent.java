package com.abhinav.order_service.events;

public class OrderCreatedEvent {

    private Long orderId;
    private String userId;
    private double amount;

    public OrderCreatedEvent(Long orderId, String userId, double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }
}