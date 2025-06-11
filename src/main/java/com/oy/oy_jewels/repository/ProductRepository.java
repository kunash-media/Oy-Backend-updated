package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByProductCategory(String productCategory);
    List<ProductEntity> findByProductStock(String productStock);
    List<ProductEntity> findByProductTitleContainingIgnoreCase(String productTitle);
    List<ProductEntity> findByShopBy(String shopBy);


    Optional<ProductEntity> findByProductTitle(String productTitle);
}
