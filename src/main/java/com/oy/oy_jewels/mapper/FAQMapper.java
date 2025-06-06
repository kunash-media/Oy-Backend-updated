package com.oy.oy_jewels.mapper;


import com.oy.oy_jewels.dto.request.FAQRequestDTO;
import com.oy.oy_jewels.dto.response.FAQResponseDTO;
import com.oy.oy_jewels.entity.FAQEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FAQMapper {

    public FAQEntity requestDtoToEntity(FAQRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        FAQEntity entity = new FAQEntity();
        entity.setFaq1Title(requestDTO.getFaq1Title());
        entity.setFaq1Description(requestDTO.getFaq1Description());
        entity.setFaq2Title(requestDTO.getFaq2Title());
        entity.setFaq2Description(requestDTO.getFaq2Description());
        entity.setFaq3Title(requestDTO.getFaq3Title());
        entity.setFaq3Description(requestDTO.getFaq3Description());
        entity.setFaq4Title(requestDTO.getFaq4Title());
        entity.setFaq4Description(requestDTO.getFaq4Description());
        entity.setFaq5Title(requestDTO.getFaq5Title());
        entity.setFaq5Description(requestDTO.getFaq5Description());
        entity.setFaq6Title(requestDTO.getFaq6Title());
        entity.setFaq6Description(requestDTO.getFaq6Description());

        return entity;
    }

    public FAQResponseDTO entityToResponseDto(FAQEntity entity) {
        if (entity == null) {
            return null;
        }

        FAQResponseDTO responseDTO = new FAQResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setFaq1Title(entity.getFaq1Title());
        responseDTO.setFaq1Description(entity.getFaq1Description());
        responseDTO.setFaq2Title(entity.getFaq2Title());
        responseDTO.setFaq2Description(entity.getFaq2Description());
        responseDTO.setFaq3Title(entity.getFaq3Title());
        responseDTO.setFaq3Description(entity.getFaq3Description());
        responseDTO.setFaq4Title(entity.getFaq4Title());
        responseDTO.setFaq4Description(entity.getFaq4Description());
        responseDTO.setFaq5Title(entity.getFaq5Title());
        responseDTO.setFaq5Description(entity.getFaq5Description());
        responseDTO.setFaq6Title(entity.getFaq6Title());
        responseDTO.setFaq6Description(entity.getFaq6Description());

        return responseDTO;
    }

    public List<FAQResponseDTO> entityListToResponseDtoList(List<FAQEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::entityToResponseDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequestDto(FAQEntity entity, FAQRequestDTO requestDTO) {
        if (entity == null || requestDTO == null) {
            return;
        }

        if (requestDTO.getFaq1Title() != null) {
            entity.setFaq1Title(requestDTO.getFaq1Title());
        }
        if (requestDTO.getFaq1Description() != null) {
            entity.setFaq1Description(requestDTO.getFaq1Description());
        }
        if (requestDTO.getFaq2Title() != null) {
            entity.setFaq2Title(requestDTO.getFaq2Title());
        }
        if (requestDTO.getFaq2Description() != null) {
            entity.setFaq2Description(requestDTO.getFaq2Description());
        }
        if (requestDTO.getFaq3Title() != null) {
            entity.setFaq3Title(requestDTO.getFaq3Title());
        }
        if (requestDTO.getFaq3Description() != null) {
            entity.setFaq3Description(requestDTO.getFaq3Description());
        }
        if (requestDTO.getFaq4Title() != null) {
            entity.setFaq4Title(requestDTO.getFaq4Title());
        }
        if (requestDTO.getFaq4Description() != null) {
            entity.setFaq4Description(requestDTO.getFaq4Description());
        }
        if (requestDTO.getFaq5Title() != null) {
            entity.setFaq5Title(requestDTO.getFaq5Title());
        }
        if (requestDTO.getFaq5Description() != null) {
            entity.setFaq5Description(requestDTO.getFaq5Description());
        }
        if (requestDTO.getFaq6Title() != null) {
            entity.setFaq6Title(requestDTO.getFaq6Title());
        }
        if (requestDTO.getFaq6Description() != null) {
            entity.setFaq6Description(requestDTO.getFaq6Description());
        }
    }
}