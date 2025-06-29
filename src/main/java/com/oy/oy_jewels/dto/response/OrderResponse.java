package com.oy.oy_jewels.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oy.oy_jewels.entity.OrderEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderResponse {
    private boolean success;
    private Long orderId;
    private BigDecimal total;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    private List<OrderItemResponse> items;
    private String message;
    private String error;
    private Long productId; // For error responses

    // Success constructor
    public OrderResponse(OrderEntity order) {
        this.success = true;
        this.orderId = order.getOrderId();
        this.total = order.getTotalAmount();
        this.status = order.getOrderStatus();
        this.orderDate = order.getOrderDate();
        this.deliveryDate = order.getDeliveryDate();
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList());
        this.message = "Order placed successfully!";
    }

    // Error constructor
    public OrderResponse(String error, String message, Long productId) {
        this.success = false;
        this.error = error;
        this.message = message;
        this.productId = productId;
    }

    // Constructors
    public OrderResponse() {}

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}