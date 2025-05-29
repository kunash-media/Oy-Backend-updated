package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.PrivacyPolicyEntity;
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
            entity.setPolicyDescription(privacyPolicy.getPolicyDescription());
            entity.setPolicyTitle1(privacyPolicy.getPolicyTitle1());
            entity.setPolicyDescription1(privacyPolicy.getPolicyDescription1());
            entity.setPolicyTitle2(privacyPolicy.getPolicyTitle2());
            entity.setPolicyDescription2(privacyPolicy.getPolicyDescription2());
            entity.setPolicyTitle3(privacyPolicy.getPolicyTitle3());
            entity.setPolicyDescription3(privacyPolicy.getPolicyDescription3());
            entity.setPolicyTitle4(privacyPolicy.getPolicyTitle4());
            entity.setPolicyDescription4(privacyPolicy.getPolicyDescription4());
            entity.setPolicyTitle5(privacyPolicy.getPolicyTitle5());
            entity.setPolicyDescription5(privacyPolicy.getPolicyDescription5());
            entity.setPolicyTitle6(privacyPolicy.getPolicyTitle6());
            entity.setPolicyDescription6(privacyPolicy.getPolicyDescription6());
            entity.setPolicyTitle7(privacyPolicy.getPolicyTitle7());
            entity.setPolicyDescription7(privacyPolicy.getPolicyDescription7());
            entity.setPolicyTitle8(privacyPolicy.getPolicyTitle8());
            entity.setPolicyDescription8(privacyPolicy.getPolicyDescription8());
            entity.setPolicyTitle9(privacyPolicy.getPolicyTitle9());
            entity.setPolicyDescription9(privacyPolicy.getPolicyDescription9());
            entity.setPolicyTitle10(privacyPolicy.getPolicyTitle10());
            entity.setPolicyDescription10(privacyPolicy.getPolicyDescription10());
            entity.setPageTitle11(privacyPolicy.getPageTitle11());
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
}

