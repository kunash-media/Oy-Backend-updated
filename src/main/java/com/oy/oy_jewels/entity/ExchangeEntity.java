package com.oy.oy_jewels.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchanges_product")
public class ExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "shiprocket_exchange_id")
    private String shiprocketExchangeId;

    @Column(name = "order_item_ids")
    private String orderItemIds; // Comma-separated list of order item IDs

    @Column(name = "new_product_id")
    private Long newProductId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "exchange_status")
    private String exchangeStatus; // initiated, approved, completed, rejected, etc.

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public ExchangeEntity() {}

    // Getters and Setters
    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
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

    public String getShiprocketExchangeId() {
        return shiprocketExchangeId;
    }

    public void setShiprocketExchangeId(String shiprocketExchangeId) {
        this.shiprocketExchangeId = shiprocketExchangeId;
    }

    public String getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(String orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public Long getNewProductId() {
        return newProductId;
    }

    public void setNewProductId(Long newProductId) {
        this.newProductId = newProductId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}