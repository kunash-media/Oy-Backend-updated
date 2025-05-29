package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.FAQEntity;

import java.util.List;

public interface FAQService {

    FAQEntity createFAQ(FAQEntity faqEntity);

    FAQEntity getFAQById(Long id);

    List<FAQEntity> getAllFAQs();

    FAQEntity updateFAQ(Long id, FAQEntity faqEntity);

    void deleteFAQ(Long id);
}