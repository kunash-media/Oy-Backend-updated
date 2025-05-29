package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.CancellationEntity;

import java.util.List;

public interface CancellationService {

    // Create new cancellation policy
    CancellationEntity createCancellationPolicy(CancellationEntity cancellationEntity);

    // Get all cancellation policies
    List<CancellationEntity> getAllCancellationPolicies();

    // Get cancellation policy by ID
    CancellationEntity getCancellationPolicyById(Long id);

    // Update cancellation policy
    CancellationEntity updateCancellationPolicy(Long id, CancellationEntity cancellationEntity);

    // Delete cancellation policy
    void deleteCancellationPolicy(Long id);

    // Search by title
    List<CancellationEntity> searchByTitle(String title);
}
