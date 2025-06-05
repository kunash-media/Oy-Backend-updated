package com.oy.oy_jewels.mapper;

// PrivacyPolicyMapper.java


import com.oy.oy_jewels.dto.request.PrivacyPolicyRequestDTO;
import com.oy.oy_jewels.dto.response.PrivacyPolicyResponseDTO;
import com.oy.oy_jewels.entity.PrivacyPolicyEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrivacyPolicyMapper {

    public PrivacyPolicyEntity toEntity(PrivacyPolicyRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        PrivacyPolicyEntity entity = new PrivacyPolicyEntity();
        entity.setPolicyDescription(requestDTO.getPolicyDescription());
        entity.setPolicyTitle1(requestDTO.getPolicyTitle1());
        entity.setPolicyDescription1(requestDTO.getPolicyDescription1());
        entity.setPolicyTitle2(requestDTO.getPolicyTitle2());
        entity.setPolicyDescription2(requestDTO.getPolicyDescription2());
        entity.setPolicyTitle3(requestDTO.getPolicyTitle3());
        entity.setPolicyDescription3(requestDTO.getPolicyDescription3());
        entity.setPolicyTitle4(requestDTO.getPolicyTitle4());
        entity.setPolicyDescription4(requestDTO.getPolicyDescription4());
        entity.setPolicyTitle5(requestDTO.getPolicyTitle5());
        entity.setPolicyDescription5(requestDTO.getPolicyDescription5());
        entity.setPolicyTitle6(requestDTO.getPolicyTitle6());
        entity.setPolicyDescription6(requestDTO.getPolicyDescription6());
        entity.setPolicyTitle7(requestDTO.getPolicyTitle7());
        entity.setPolicyDescription7(requestDTO.getPolicyDescription7());
        entity.setPolicyTitle8(requestDTO.getPolicyTitle8());
        entity.setPolicyDescription8(requestDTO.getPolicyDescription8());
        entity.setPolicyTitle9(requestDTO.getPolicyTitle9());
        entity.setPolicyDescription9(requestDTO.getPolicyDescription9());
        entity.setPolicyTitle10(requestDTO.getPolicyTitle10());
        entity.setPolicyDescription10(requestDTO.getPolicyDescription10());
        entity.setPageTitle11(requestDTO.getPageTitle11());

        return entity;
    }

    public PrivacyPolicyResponseDTO toResponseDTO(PrivacyPolicyEntity entity) {
        if (entity == null) {
            return null;
        }

        PrivacyPolicyResponseDTO responseDTO = new PrivacyPolicyResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setPolicyDescription(entity.getPolicyDescription());
        responseDTO.setPolicyTitle1(entity.getPolicyTitle1());
        responseDTO.setPolicyDescription1(entity.getPolicyDescription1());
        responseDTO.setPolicyTitle2(entity.getPolicyTitle2());
        responseDTO.setPolicyDescription2(entity.getPolicyDescription2());
        responseDTO.setPolicyTitle3(entity.getPolicyTitle3());
        responseDTO.setPolicyDescription3(entity.getPolicyDescription3());
        responseDTO.setPolicyTitle4(entity.getPolicyTitle4());
        responseDTO.setPolicyDescription4(entity.getPolicyDescription4());
        responseDTO.setPolicyTitle5(entity.getPolicyTitle5());
        responseDTO.setPolicyDescription5(entity.getPolicyDescription5());
        responseDTO.setPolicyTitle6(entity.getPolicyTitle6());
        responseDTO.setPolicyDescription6(entity.getPolicyDescription6());
        responseDTO.setPolicyTitle7(entity.getPolicyTitle7());
        responseDTO.setPolicyDescription7(entity.getPolicyDescription7());
        responseDTO.setPolicyTitle8(entity.getPolicyTitle8());
        responseDTO.setPolicyDescription8(entity.getPolicyDescription8());
        responseDTO.setPolicyTitle9(entity.getPolicyTitle9());
        responseDTO.setPolicyDescription9(entity.getPolicyDescription9());
        responseDTO.setPolicyTitle10(entity.getPolicyTitle10());
        responseDTO.setPolicyDescription10(entity.getPolicyDescription10());
        responseDTO.setPageTitle11(entity.getPageTitle11());

        return responseDTO;
    }

    public List<PrivacyPolicyResponseDTO> toResponseDTOList(List<PrivacyPolicyEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(PrivacyPolicyEntity entity, PrivacyPolicyRequestDTO requestDTO) {
        if (entity == null || requestDTO == null) {
            return;
        }

        entity.setPolicyDescription(requestDTO.getPolicyDescription());
        entity.setPolicyTitle1(requestDTO.getPolicyTitle1());
        entity.setPolicyDescription1(requestDTO.getPolicyDescription1());
        entity.setPolicyTitle2(requestDTO.getPolicyTitle2());
        entity.setPolicyDescription2(requestDTO.getPolicyDescription2());
        entity.setPolicyTitle3(requestDTO.getPolicyTitle3());
        entity.setPolicyDescription3(requestDTO.getPolicyDescription3());
        entity.setPolicyTitle4(requestDTO.getPolicyTitle4());
        entity.setPolicyDescription4(requestDTO.getPolicyDescription4());
        entity.setPolicyTitle5(requestDTO.getPolicyTitle5());
        entity.setPolicyDescription5(requestDTO.getPolicyDescription5());
        entity.setPolicyTitle6(requestDTO.getPolicyTitle6());
        entity.setPolicyDescription6(requestDTO.getPolicyDescription6());
        entity.setPolicyTitle7(requestDTO.getPolicyTitle7());
        entity.setPolicyDescription7(requestDTO.getPolicyDescription7());
        entity.setPolicyTitle8(requestDTO.getPolicyTitle8());
        entity.setPolicyDescription8(requestDTO.getPolicyDescription8());
        entity.setPolicyTitle9(requestDTO.getPolicyTitle9());
        entity.setPolicyDescription9(requestDTO.getPolicyDescription9());
        entity.setPolicyTitle10(requestDTO.getPolicyTitle10());
        entity.setPolicyDescription10(requestDTO.getPolicyDescription10());
        entity.setPageTitle11(requestDTO.getPageTitle11());
    }
}
