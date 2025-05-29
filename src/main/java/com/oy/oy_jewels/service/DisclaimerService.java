package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.DisclaimerEntity;

import java.util.List;

public interface DisclaimerService {

    // Create new disclaimer
    DisclaimerEntity createDisclaimer(DisclaimerEntity disclaimerEntity);

    // Get all disclaimers
    List<DisclaimerEntity> getAllDisclaimers();

    // Get disclaimer by ID
    DisclaimerEntity getDisclaimerById(Long id);

    // Update disclaimer
    DisclaimerEntity updateDisclaimer(Long id, DisclaimerEntity disclaimerEntity);

    // Delete disclaimer
    void deleteDisclaimer(Long id);

    // Search by title
    List<DisclaimerEntity> searchByTitle(String title);

    // Get disclaimer by section number
    DisclaimerEntity getDisclaimerBySectionNumber(Integer sectionNumber);

    // Get all disclaimers ordered by section number
    List<DisclaimerEntity> getAllDisclaimersOrdered();

    // Get disclaimers without titles
    List<DisclaimerEntity> getDisclaimersWithoutTitles();
}
