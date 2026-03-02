package com.abhinav.order_service.dto;

import com.abhinav.order_service.domain.OrderStatus;

public class UpdateOrderStatusRequest {
    private OrderStatus status;

    public OrderStatus getStatus(){
        return status;
    }

    public void setStatus(OrderStatus orderStatus){
        this.status=orderStatus;
    }
}
