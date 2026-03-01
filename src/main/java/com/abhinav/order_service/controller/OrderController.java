package com.abhinav.order_service.controller;

import com.abhinav.order_service.domain.Order;
import com.abhinav.order_service.dto.CreateOrderRequest;
import com.abhinav.order_service.dto.OrderResponse;
import com.abhinav.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
