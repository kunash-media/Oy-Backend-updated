package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findByOrder_OrderId(Long orderId);
    List<OrderItemEntity> findByProduct_ProductId(Long productId);


}
