package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    List<ProductEntity> findByProductCategory(String productCategory);
    List<ProductEntity> findByProductStock(String productStock);
    List<ProductEntity> findByProductTitleContainingIgnoreCase(String productTitle);
    List<ProductEntity> findByShopBy(String shopBy);

    Optional<ProductEntity> findBySkuNo(String skuNo);
    Optional<ProductEntity> findByProductTitle(String productTitle);


    // Override default delete methods to prevent accidental hard deletes
    @Override
    @Modifying
    @Query("UPDATE ProductEntity p SET p.isDeleted = true WHERE p.productId = ?1")
    void deleteById(Long productId);

    @Override
    @Modifying
    @Query("UPDATE ProductEntity p SET p.isDeleted = true WHERE p = ?1")
    void delete(ProductEntity entity);

    // Include deleted products when needed (for admin purposes)
    @Query("SELECT p FROM ProductEntity p WHERE p.productId = ?1 AND p.isDeleted = true")
    Optional<ProductEntity> findDeletedById(Long productId);

    default List<ProductEntity> findAllActive() {
        return findAll(ProductEntity.notDeleted());
    }
}
