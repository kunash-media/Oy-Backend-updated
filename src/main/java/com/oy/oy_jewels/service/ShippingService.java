package com.oy.oy_jewels.service;


import com.oy.oy_jewels.dto.request.ShippingRequestDTO;
import com.oy.oy_jewels.dto.response.ShippingResponseDTO;

import java.util.List;

public interface ShippingService {

    ShippingResponseDTO createShipping(ShippingRequestDTO shippingRequest);

    List<ShippingResponseDTO> getAllShippings();

    ShippingResponseDTO getShippingById(Long id);

    ShippingResponseDTO updateShipping(Long id, ShippingRequestDTO shippingRequest);

    void deleteShipping(Long id);

    List<ShippingResponseDTO> searchByTitle(String title);

    List<ShippingResponseDTO> searchByDescription(String description);
}