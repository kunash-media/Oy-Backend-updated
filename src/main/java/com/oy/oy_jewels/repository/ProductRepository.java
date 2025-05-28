package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Find products by category
    List<ProductEntity> findByCategory(String category);

    // Find products by stock status
    List<ProductEntity> findByStock(String stock);

    // Find products by product name containing
    List<ProductEntity> findByProductNameContainingIgnoreCase(String productName);

    // Find products by shop by
    List<ProductEntity> findByShopBy(String shopBy);
}
