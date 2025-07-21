package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.ShippingAddressDTO;

import java.util.List;

public interface ShippingAddressService {

    ShippingAddressDTO createAddress(Long userId, ShippingAddressDTO addressDTO);

    List<ShippingAddressDTO> getAddressesByUserId(Long userId);

    List<ShippingAddressDTO> getAllAddresses();

    ShippingAddressDTO updateAddress(Long userId, Long shippingId, ShippingAddressDTO addressDTO);

    void deleteAddress(Long userId, Long shippingId);
}
