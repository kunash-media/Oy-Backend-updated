package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.PrivacyPolicyEntity;

import java.util.List;
import java.util.Optional;

public interface PrivacyPolicyService {

    PrivacyPolicyEntity createPrivacyPolicy(PrivacyPolicyEntity privacyPolicy);

    List<PrivacyPolicyEntity> getAllPrivacyPolicies();

    Optional<PrivacyPolicyEntity> getPrivacyPolicyById(Long id);

    PrivacyPolicyEntity updatePrivacyPolicy(Long id, PrivacyPolicyEntity privacyPolicy);

    void deletePrivacyPolicy(Long id);

    PrivacyPolicyEntity getLatestPrivacyPolicy();
}

