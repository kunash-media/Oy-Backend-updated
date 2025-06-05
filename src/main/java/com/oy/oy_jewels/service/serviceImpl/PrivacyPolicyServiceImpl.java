package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.entity.PrivacyPolicyEntity;
import com.oy.oy_jewels.mapper.PrivacyPolicyMapper;
import com.oy.oy_jewels.repository.PrivacyPolicyRepository;
import com.oy.oy_jewels.service.PrivacyPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivacyPolicyServiceImpl implements PrivacyPolicyService {

    @Autowired
    private PrivacyPolicyRepository privacyPolicyRepository;

    @Autowired
    private PrivacyPolicyMapper privacyPolicyMapper;

    @Override
    public PrivacyPolicyEntity createPrivacyPolicy(PrivacyPolicyEntity privacyPolicy) {
        return privacyPolicyRepository.save(privacyPolicy);
    }

    @Override
    public List<PrivacyPolicyEntity> getAllPrivacyPolicies() {
        return privacyPolicyRepository.findAll();
    }

    @Override
    public Optional<PrivacyPolicyEntity> getPrivacyPolicyById(Long id) {
        return privacyPolicyRepository.findById(id);
    }

    @Override
    public PrivacyPolicyEntity updatePrivacyPolicy(Long id, PrivacyPolicyEntity privacyPolicy) {
        Optional<PrivacyPolicyEntity> existingPolicy = privacyPolicyRepository.findById(id);
        if (existingPolicy.isPresent()) {
            PrivacyPolicyEntity entity = existingPolicy.get();
            // Use mapper to update entity from the provided policy data
            privacyPolicyMapper.updateEntityFromDTO(entity, convertEntityToRequestDTO(privacyPolicy));
            return privacyPolicyRepository.save(entity);
        }
        return null;
    }

    @Override
    public void deletePrivacyPolicy(Long id) {
        privacyPolicyRepository.deleteById(id);
    }

    @Override
    public PrivacyPolicyEntity getLatestPrivacyPolicy() {
        List<PrivacyPolicyEntity> policies = privacyPolicyRepository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
        ).getContent();

        if (!policies.isEmpty()) {
            return policies.get(0);
        }
        return null;
    }

    // Helper method to convert entity to request DTO for mapper usage
    private com.oy.oy_jewels.dto.request.PrivacyPolicyRequestDTO convertEntityToRequestDTO(PrivacyPolicyEntity entity) {
        if (entity == null) {
            return null;
        }

        com.oy.oy_jewels.dto.request.PrivacyPolicyRequestDTO requestDTO = new com.oy.oy_jewels.dto.request.PrivacyPolicyRequestDTO();
        requestDTO.setPolicyDescription(entity.getPolicyDescription());
        requestDTO.setPolicyTitle1(entity.getPolicyTitle1());
        requestDTO.setPolicyDescription1(entity.getPolicyDescription1());
        requestDTO.setPolicyTitle2(entity.getPolicyTitle2());
        requestDTO.setPolicyDescription2(entity.getPolicyDescription2());
        requestDTO.setPolicyTitle3(entity.getPolicyTitle3());
        requestDTO.setPolicyDescription3(entity.getPolicyDescription3());
        requestDTO.setPolicyTitle4(entity.getPolicyTitle4());
        requestDTO.setPolicyDescription4(entity.getPolicyDescription4());
        requestDTO.setPolicyTitle5(entity.getPolicyTitle5());
        requestDTO.setPolicyDescription5(entity.getPolicyDescription5());
        requestDTO.setPolicyTitle6(entity.getPolicyTitle6());
        requestDTO.setPolicyDescription6(entity.getPolicyDescription6());
        requestDTO.setPolicyTitle7(entity.getPolicyTitle7());
        requestDTO.setPolicyDescription7(entity.getPolicyDescription7());
        requestDTO.setPolicyTitle8(entity.getPolicyTitle8());
        requestDTO.setPolicyDescription8(entity.getPolicyDescription8());
        requestDTO.setPolicyTitle9(entity.getPolicyTitle9());
        requestDTO.setPolicyDescription9(entity.getPolicyDescription9());
        requestDTO.setPolicyTitle10(entity.getPolicyTitle10());
        requestDTO.setPolicyDescription10(entity.getPolicyDescription10());
        requestDTO.setPageTitle11(entity.getPageTitle11());

        return requestDTO;
    }
}