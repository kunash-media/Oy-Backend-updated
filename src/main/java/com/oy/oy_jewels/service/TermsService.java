package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.TermsEntity;

import java.util.List;

public interface TermsService {

    // Create new terms policy
    TermsEntity createTermsPolicy(TermsEntity termsEntity);

    // Get all terms policies
    List<TermsEntity> getAllTermsPolicies();

    // Get terms policy by ID
    TermsEntity getTermsPolicyById(Long id);

    // Update terms policy
    TermsEntity updateTermsPolicy(Long id, TermsEntity termsEntity);

    // Delete terms policy
    void deleteTermsPolicy(Long id);

    // Search by title
    List<TermsEntity> searchByTitle(String title);
}
