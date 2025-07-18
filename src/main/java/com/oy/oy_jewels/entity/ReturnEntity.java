package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "return_orders")
public class ReturnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "shiprocket_return_id")
    private String shiprocketReturnId;

    @Column(name = "order_item_ids")
    private String orderItemIds; // Comma-separated list of order item IDs

    @Column(name = "reason")
    private String reason;

    @Column(name = "return_status")
    private String returnStatus; // initiated, approved, completed, rejected, etc.

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public ReturnEntity() {}

    // Getters and Setters
    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public String getShiprocketReturnId() {
        return shiprocketReturnId;
    }

    public void setShiprocketReturnId(String shiprocketReturnId) {
        this.shiprocketReturnId = shiprocketReturnId;
    }

    public String getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(String orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}