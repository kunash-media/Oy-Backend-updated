package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.DisclaimerRequestDTO;
import com.oy.oy_jewels.dto.response.DisclaimerResponseDTO;

import java.util.List;

public interface DisclaimerService {

    // Create new disclaimer
    DisclaimerResponseDTO createDisclaimer(DisclaimerRequestDTO requestDTO);

    // Get all disclaimers
    List<DisclaimerResponseDTO> getAllDisclaimers();

    // Get disclaimer by ID
    DisclaimerResponseDTO getDisclaimerById(Long id);

    // Update disclaimer
    DisclaimerResponseDTO updateDisclaimer(Long id, DisclaimerRequestDTO requestDTO);

    // Delete disclaimer
    void deleteDisclaimer(Long id);

    // Search by title
    List<DisclaimerResponseDTO> searchByTitle(String title);

    // Get disclaimer by section number
    DisclaimerResponseDTO getDisclaimerBySectionNumber(Integer sectionNumber);

    // Get all disclaimers ordered by section number
    List<DisclaimerResponseDTO> getAllDisclaimersOrdered();

    // Get disclaimers without titles
    List<DisclaimerResponseDTO> getDisclaimersWithoutTitles();
}
