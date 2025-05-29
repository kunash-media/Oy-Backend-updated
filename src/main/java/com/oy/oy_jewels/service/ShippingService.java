package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.ShippingEntity;

import java.util.List;
import java.util.Optional;

public interface ShippingService {

    ShippingEntity createShipping(ShippingEntity shipping);

    List<ShippingEntity> getAllShippings();

    Optional<ShippingEntity> getShippingById(Long id);

    ShippingEntity updateShipping(Long id, ShippingEntity shipping);

    void deleteShipping(Long id);

    List<ShippingEntity> searchByTitle(String title);

    List<ShippingEntity> searchByDescription(String description);
}
