package com.abhinav.order_service.dto;

public class PaymentRequest {

    private Long orderId;
    private Double amount;

    public Long getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }
}
