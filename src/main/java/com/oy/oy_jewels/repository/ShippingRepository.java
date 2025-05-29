package com.oy.oy_jewels.repository;

import com.oy.oy_jewels.entity.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {

    @Query("SELECT s FROM ShippingEntity s WHERE s.shippingTitle1 LIKE %?1% OR s.shippingTitle2 LIKE %?1% OR s.shippingTitle3 LIKE %?1% OR s.shippingTitle4 LIKE %?1% OR s.shippingTitle5 LIKE %?1%")
    List<ShippingEntity> findByShippingTitleContaining(String title);

    @Query("SELECT s FROM ShippingEntity s WHERE s.shippingDescription1 LIKE %?1% OR s.shippingDescription2 LIKE %?1% OR s.shippingDescription3 LIKE %?1% OR s.shippingDescription4 LIKE %?1% OR s.shippingDescription5 LIKE %?1%")
    List<ShippingEntity> findByShippingDescriptionContaining(String description);
}
