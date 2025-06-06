package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.FAQEntity;

import java.util.List;

public interface FAQService {

    // Create a new FAQ
    FAQEntity createFAQ(FAQEntity faq);

    // Get all active FAQs
    List<FAQEntity> getAllActiveFAQs();

    // Get FAQ by ID
    FAQEntity getFAQById(Long id);

    // Update FAQ
    FAQEntity updateFAQ(Long id, FAQEntity faq);

    // Delete FAQ (soft delete by setting isActive to false)
    void deleteFAQ(Long id);

    // Get FAQs by category
    List<FAQEntity> getFAQsByCategory(String category);

    // Search FAQs by keyword
    List<FAQEntity> searchFAQs(String keyword);

    // Get all active categories
    List<String> getAllActiveCategories();

    // Get FAQs by multiple categories
    List<FAQEntity> getFAQsByCategories(List<String> categories);

    // Reorder FAQs
    void reorderFAQs(List<Long> faqIds);

    // Toggle FAQ active status
    FAQEntity toggleFAQStatus(Long id);
}
