package com.oy.oy_jewels.mapper;


import com.oy.oy_jewels.dto.request.CancellationRequestDTO;
import com.oy.oy_jewels.dto.response.CancellationResponseDTO;
import com.oy.oy_jewels.entity.CancellationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CancellationMapper {

    // Convert Entity to Response DTO
    public CancellationResponseDTO toResponseDTO(CancellationEntity entity) {
        if (entity == null) {
            return null;
        }

        CancellationResponseDTO dto = new CancellationResponseDTO();
        dto.setId(entity.getId());
        dto.setCancellationTitle(entity.getCancellationTitle());
        dto.setCancellationDescription(entity.getCancellationDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    // Convert Request DTO to Entity
    public CancellationEntity toEntity(CancellationRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        CancellationEntity entity = new CancellationEntity();
        entity.setCancellationTitle(dto.getCancellationTitle());
        entity.setCancellationDescription(dto.getCancellationDescription());

        return entity;
    }

    // Update Entity from Request DTO
    public void updateEntityFromDTO(CancellationRequestDTO dto, CancellationEntity entity) {
        if (dto != null && entity != null) {
            entity.setCancellationTitle(dto.getCancellationTitle());
            entity.setCancellationDescription(dto.getCancellationDescription());
        }
    }

    // Convert List of Entities to List of Response DTOs
    public List<CancellationResponseDTO> toResponseDTOList(List<CancellationEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
