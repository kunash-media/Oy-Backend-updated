package com.oy.oy_jewels.mapper;

import com.oy.oy_jewels.dto.request.DisclaimerRequestDTO;
import com.oy.oy_jewels.dto.response.DisclaimerResponseDTO;
import com.oy.oy_jewels.entity.DisclaimerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisclaimerMapper {

    // Convert RequestDTO to Entity
    public DisclaimerEntity requestDtoToEntity(DisclaimerRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        DisclaimerEntity entity = new DisclaimerEntity();
        entity.setDisclaimerTitle(requestDTO.getDisclaimerTitle());
        entity.setDisclaimerDescription(requestDTO.getDisclaimerDescription());
        entity.setSectionNumber(requestDTO.getSectionNumber());

        return entity;
    }

    // Convert Entity to ResponseDTO
    public DisclaimerResponseDTO entityToResponseDto(DisclaimerEntity entity) {
        if (entity == null) {
            return null;
        }

        DisclaimerResponseDTO responseDTO = new DisclaimerResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setDisclaimerTitle(entity.getDisclaimerTitle());
        responseDTO.setDisclaimerDescription(entity.getDisclaimerDescription());
        responseDTO.setSectionNumber(entity.getSectionNumber());

        return responseDTO;
    }

    // Convert List of Entities to List of ResponseDTOs
    public List<DisclaimerResponseDTO> entityListToResponseDtoList(List<DisclaimerEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }

    // Update existing entity with RequestDTO data
    public void updateEntityFromRequestDto(DisclaimerEntity entity, DisclaimerRequestDTO requestDTO) {
        if (entity != null && requestDTO != null) {
            entity.setDisclaimerTitle(requestDTO.getDisclaimerTitle());
            entity.setDisclaimerDescription(requestDTO.getDisclaimerDescription());
            entity.setSectionNumber(requestDTO.getSectionNumber());
        }
    }
}
