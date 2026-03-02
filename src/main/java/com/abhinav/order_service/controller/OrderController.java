package com.abhinav.order_service.controller;

import com.abhinav.order_service.domain.Order;
import com.abhinav.order_service.dto.CreateOrderRequest;
import com.abhinav.order_service.dto.OrderResponse;
import com.abhinav.order_service.dto.UpdateOrderStatusRequest;
import com.abhinav.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusRequest request){
        orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok("Order status Updated Successfully!");
    }

    @GetMapping
    public Page<OrderResponse> getOrdersByUser(
            @RequestParam String userId,
            @PageableDefault(
                    size = 5,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable){
        return orderService.getOrdersByUser(userId, pageable);
    }
}
