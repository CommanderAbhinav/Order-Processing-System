package com.abhinav.order_service.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Order {

        @Id
        @GeneratedValue
        private Long id;

        private String userId;

        private String productName;

        private int quantity;

        @Version
        private Long version;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getProductName() {
                return productName;
        }

        public void setProductName(String productName) {
                this.productName = productName;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        public double getAmount() {
                return amount;
        }

        public void setAmount(double amount) {
                this.amount = amount;
        }

        public OrderStatus getStatus() {
                return status;
        }

        public void setStatus(OrderStatus status) {
                this.status = status;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        private double amount;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

        private LocalDateTime createdAt;

        @PrePersist
        public void prePersist(){
            this.createdAt = LocalDateTime.now();
            this.status = OrderStatus.CREATED;
        }

        public Order(){}
}
