package com.oy.oy_jewels.mapper;


import com.oy.oy_jewels.dto.request.AdminRequestDTO;
import com.oy.oy_jewels.dto.request.AdminUpdateDTO;
import com.oy.oy_jewels.dto.response.AdminResponseDTO;
import com.oy.oy_jewels.entity.AdminEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminMapper {

    // Convert AdminRequestDTO to AdminEntity
    public AdminEntity toEntity(AdminRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        AdminEntity admin = new AdminEntity();
        admin.setName(requestDTO.getName());
        admin.setEmail(requestDTO.getEmail());
        admin.setPassword(requestDTO.getPassword());
        return admin;
    }

    // Convert AdminEntity to AdminResponseDTO
    public AdminResponseDTO toResponseDTO(AdminEntity entity) {
        if (entity == null) {
            return null;
        }

        AdminResponseDTO responseDTO = new AdminResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setCreatedAt(entity.getCreatedAt());
        responseDTO.setUpdatedAt(entity.getUpdatedAt());
        return responseDTO;
    }

    // Convert List of AdminEntity to List of AdminResponseDTO
    public List<AdminResponseDTO> toResponseDTOList(List<AdminEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Update AdminEntity with AdminUpdateDTO
    public void updateEntityFromDTO(AdminUpdateDTO updateDTO, AdminEntity entity) {
        if (entity == null || updateDTO == null) {
            return;
        }

        if (updateDTO.getName() != null && !updateDTO.getName().trim().isEmpty()) {
            entity.setName(updateDTO.getName());
        }
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().trim().isEmpty()) {
            entity.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().trim().isEmpty()) {
            entity.setPassword(updateDTO.getPassword());
        }
    }
}