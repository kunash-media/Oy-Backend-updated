package com.oy.oy_jewels.mapper;

import com.oy.oy_jewels.dto.request.CheckOutRequestDTO;
import com.oy.oy_jewels.dto.response.CheckOutResponseDTO;
import com.oy.oy_jewels.entity.CheckOutEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckOutMapper {

    public CheckOutEntity toEntity(CheckOutRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        CheckOutEntity entity = new CheckOutEntity();
        entity.setFullName(requestDTO.getFullName());
        entity.setEmail(requestDTO.getEmail());
        entity.setPhoneNumber(requestDTO.getPhoneNumber());
        entity.setAlternatePhoneNumber(requestDTO.getAlternatePhoneNumber());
        entity.setStreetAddress(requestDTO.getStreetAddress());
        entity.setCity(requestDTO.getCity());
        entity.setCountry(requestDTO.getCountry());
        entity.setZipPostalCode(requestDTO.getZipPostalCode());
        entity.setOrderSummaryTitle(requestDTO.getOrderSummaryTitle());
        entity.setCouponCode(requestDTO.getCouponCode());
        entity.setSubtotalLabel(requestDTO.getSubtotalLabel());
        entity.setShippingLabel(requestDTO.getShippingLabel());
        entity.setTaxLabel(requestDTO.getTaxLabel());
        entity.setTotalCostLabel(requestDTO.getTotalCostLabel());

        return entity;
    }

    public CheckOutResponseDTO toResponseDTO(CheckOutEntity entity) {
        if (entity == null) {
            return null;
        }

        CheckOutResponseDTO responseDTO = new CheckOutResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setFullName(entity.getFullName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setPhoneNumber(entity.getPhoneNumber());
        responseDTO.setAlternatePhoneNumber(entity.getAlternatePhoneNumber());
        responseDTO.setStreetAddress(entity.getStreetAddress());
        responseDTO.setCity(entity.getCity());
        responseDTO.setCountry(entity.getCountry());
        responseDTO.setZipPostalCode(entity.getZipPostalCode());
        responseDTO.setOrderSummaryTitle(entity.getOrderSummaryTitle());
        responseDTO.setCouponCode(entity.getCouponCode());
        responseDTO.setSubtotalLabel(entity.getSubtotalLabel());
        responseDTO.setShippingLabel(entity.getShippingLabel());
        responseDTO.setTaxLabel(entity.getTaxLabel());
        responseDTO.setTotalCostLabel(entity.getTotalCostLabel());

        return responseDTO;
    }

    public List<CheckOutResponseDTO> toResponseDTOList(List<CheckOutEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequestDTO(CheckOutEntity entity, CheckOutRequestDTO requestDTO) {
        if (entity == null || requestDTO == null) {
            return;
        }

        entity.setFullName(requestDTO.getFullName());
        entity.setEmail(requestDTO.getEmail());
        entity.setPhoneNumber(requestDTO.getPhoneNumber());
        entity.setAlternatePhoneNumber(requestDTO.getAlternatePhoneNumber());
        entity.setStreetAddress(requestDTO.getStreetAddress());
        entity.setCity(requestDTO.getCity());
        entity.setCountry(requestDTO.getCountry());
        entity.setZipPostalCode(requestDTO.getZipPostalCode());
        entity.setOrderSummaryTitle(requestDTO.getOrderSummaryTitle());
        entity.setCouponCode(requestDTO.getCouponCode());
        entity.setSubtotalLabel(requestDTO.getSubtotalLabel());
        entity.setShippingLabel(requestDTO.getShippingLabel());
        entity.setTaxLabel(requestDTO.getTaxLabel());
        entity.setTotalCostLabel(requestDTO.getTotalCostLabel());
    }
}

