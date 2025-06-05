package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.CancellationRequestDTO;
import com.oy.oy_jewels.dto.response.CancellationResponseDTO;

import java.util.List;

public interface CancellationService {

    CancellationResponseDTO createCancellationPolicy(CancellationRequestDTO cancellationRequestDTO);

    List<CancellationResponseDTO> getAllCancellationPolicies();

    CancellationResponseDTO getCancellationPolicyById(Long id);

    CancellationResponseDTO updateCancellationPolicy(Long id, CancellationRequestDTO cancellationRequestDTO);

    void deleteCancellationPolicy(Long id);

    List<CancellationResponseDTO> searchByTitle(String title);
}