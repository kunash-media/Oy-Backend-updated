package com.oy.oy_jewels.service;


import com.oy.oy_jewels.dto.request.TermsRequestDTO;
import com.oy.oy_jewels.dto.response.TermsResponseDTO;

import java.util.List;

public interface TermsService {

    // Create new terms policy
    TermsResponseDTO createTermsPolicy(TermsRequestDTO termsRequestDTO);

    // Get all terms policies
    List<TermsResponseDTO> getAllTermsPolicies();

    // Get terms policy by ID
    TermsResponseDTO getTermsPolicyById(Long id);

    // Update terms policy
    TermsResponseDTO updateTermsPolicy(Long id, TermsRequestDTO termsRequestDTO);

    // Delete terms policy
    void deleteTermsPolicy(Long id);

    // Search by title
    List<TermsResponseDTO> searchByTitle(String title);
}
