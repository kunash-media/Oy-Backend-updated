package com.oy.oy_jewels.mapper;

import com.oy.oy_jewels.dto.request.CustomerRequestDTO;
import com.oy.oy_jewels.dto.response.CustomerResponseDTO;
import com.oy.oy_jewels.entity.CustomerDetailsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public CustomerDetailsEntity toEntity(CustomerRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        CustomerDetailsEntity entity = new CustomerDetailsEntity();
        entity.setCustomerName(requestDTO.getCustomerName());
        entity.setEmail(requestDTO.getEmail());
        entity.setMobileNumber(requestDTO.getMobileNumber());
        entity.setMaritalStatus(requestDTO.getMaritalStatus());
        entity.setBirthDate(requestDTO.getBirthDate());
        entity.setAnniversary(requestDTO.getAnniversary());
        entity.setStatus(requestDTO.getStatus());

        return entity;
    }

    public CustomerResponseDTO toResponseDTO(CustomerDetailsEntity entity) {
        if (entity == null) {
            return null;
        }

        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setUserId(entity.getUserId());
        responseDTO.setCustomerName(entity.getCustomerName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setMobileNumber(entity.getMobileNumber());
        responseDTO.setMaritalStatus(entity.getMaritalStatus());
        responseDTO.setBirthDate(entity.getBirthDate());
        responseDTO.setAnniversary(entity.getAnniversary());
        responseDTO.setStatus(entity.getStatus());

        return responseDTO;
    }

    public List<CustomerResponseDTO> toResponseDTOList(List<CustomerDetailsEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(CustomerDetailsEntity entity, CustomerRequestDTO requestDTO) {
        if (entity == null || requestDTO == null) {
            return;
        }

        entity.setCustomerName(requestDTO.getCustomerName());
        entity.setEmail(requestDTO.getEmail());
        entity.setMobileNumber(requestDTO.getMobileNumber());
        entity.setMaritalStatus(requestDTO.getMaritalStatus());
        entity.setBirthDate(requestDTO.getBirthDate());
        entity.setAnniversary(requestDTO.getAnniversary());
        entity.setStatus(requestDTO.getStatus());
    }
}