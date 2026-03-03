package com.abhinav.order_service.service;

import com.abhinav.order_service.domain.Order;
import com.abhinav.order_service.domain.OrderStatus;
import com.abhinav.order_service.dto.CreateOrderRequest;
import com.abhinav.order_service.dto.OrderResponse;
import com.abhinav.order_service.dto.PagedResponse;
import com.abhinav.order_service.exception.OrderNotFoundException;
import com.abhinav.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request){
        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setAmount(request.getAmount());

        Order saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    private OrderResponse mapToResponse(Order order) {

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setProductName(order.getProductName());
        response.setQuantity(order.getQuantity());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus().name());
        response.setCreatedAt(order.getCreatedAt());

        return response;
    }

    public OrderResponse getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() ->new OrderNotFoundException(String.valueOf(id)));
        return mapToResponse(order);
    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatus newStatus){
        Order order = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(String.valueOf(id)));
        order.setStatus(newStatus);
    }

    public PagedResponse<OrderResponse> getOrdersByUser(String userId, Pageable pageable){
        Page<Order> ordersPage = orderRepository.findByUserId(userId, pageable);
        Page<OrderResponse> mapped = ordersPage.map(this::mapToResponse);

        return new PagedRespose<>(
                mapped.getContent(),
                mapped.getNumber(),
                mapped.getSize(),
                mapped.getTotalElements(),
                mapped.getTotalPages(),
                mapped.isLast()
        );
    }
}
