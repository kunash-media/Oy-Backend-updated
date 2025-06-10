package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByProductCategory(String productCategory);
    List<ProductEntity> findByProductStock(String productStock);
    List<ProductEntity> findByProductTitleContainingIgnoreCase(String productTitle);
    List<ProductEntity> findByShopBy(String shopBy);
}
