package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.FAQRequestDTO;
import com.oy.oy_jewels.dto.response.FAQResponseDTO;

import java.util.List;

public interface FAQService {

    FAQResponseDTO createFAQ(FAQRequestDTO requestDTO);

    FAQResponseDTO getFAQById(Long id);

    List<FAQResponseDTO> getAllFAQs();

    FAQResponseDTO updateFAQ(Long id, FAQRequestDTO requestDTO);

    boolean deleteFAQ(Long id);
}