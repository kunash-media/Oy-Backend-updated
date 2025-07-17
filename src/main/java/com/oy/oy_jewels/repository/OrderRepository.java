package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // Find orders by user ID
    List<OrderEntity> findByUser_UserId(Long userId);

    // Find orders by order status
    List<OrderEntity> findByOrderStatus(String orderStatus);

    // Find orders by date range
    List<OrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    // Find orders by user and status
    List<OrderEntity> findByUser_UserIdAndOrderStatus(Long userId, String orderStatus);

    Optional<OrderEntity> findByShiprocketOrderId(String shiprocketOrderId);

    // You can also add other useful methods
    Optional<OrderEntity> findByAwbCode(String awbCode);
    Optional<OrderEntity> findByShiprocketShipmentId(String shiprocketShipmentId);

}
