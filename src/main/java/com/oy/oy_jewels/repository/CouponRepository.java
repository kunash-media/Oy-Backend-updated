package com.oy.oy_jewels.repository;


import com.oy.oy_jewels.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    Optional<CouponEntity> findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);
}