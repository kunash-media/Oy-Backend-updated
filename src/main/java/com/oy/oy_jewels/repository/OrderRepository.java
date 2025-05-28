package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // Find orders by customer name
    List<OrderEntity> findByCustomerName(String customerName);

    // Find orders by order status
    List<OrderEntity> findByOrderStatus(String orderStatus);

    // Find orders by order date
    List<OrderEntity> findByOrderDate(LocalDate orderDate);

    // Find orders by payment mode
    List<OrderEntity> findByPaymentMode(String paymentMode);

    // Find orders by user id
    List<OrderEntity> findByUser_UserId(Long userId);

    // Find orders by product id
    List<OrderEntity> findByProduct_ProductId(Long productId);
}