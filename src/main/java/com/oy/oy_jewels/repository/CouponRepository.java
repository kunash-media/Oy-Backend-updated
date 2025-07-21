package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    Optional<CouponEntity> findByCouponCode(String couponCode);

    List<CouponEntity> findByCouponType(String couponType);

    List<CouponEntity> findByStatus(String status);

    List<CouponEntity> findByIsUsed(Boolean isUsed);

    @Query("SELECT c FROM CouponEntity c WHERE c.status = 'valid' AND c.isUsed = false")
    List<CouponEntity> findAvailableCoupons();

    @Query("SELECT c FROM CouponEntity c WHERE c.couponCode = :code AND c.status = 'valid' AND c.isUsed = false")
    Optional<CouponEntity> findValidCouponByCode(@Param("code") String code);

    boolean existsByCouponCode(String couponCode);

    List<CouponEntity> findByUserUserId(Long userId);
}