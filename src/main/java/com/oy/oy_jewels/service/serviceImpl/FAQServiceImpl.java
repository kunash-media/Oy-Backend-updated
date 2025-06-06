package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.FAQEntity;
import com.oy.oy_jewels.repository.FAQRepository;
import com.oy.oy_jewels.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Override
    public FAQEntity createFAQ(FAQEntity faq) {
        // Set display order if not provided
        if (faq.getDisplayOrder() == null) {
            Integer maxOrder = faqRepository.findMaxDisplayOrder();
            faq.setDisplayOrder(maxOrder != null ? maxOrder + 1 : 1);
        }

        // Set default active status if not provided
        if (faq.getIsActive() == null) {
            faq.setIsActive(true);
        }

        return faqRepository.save(faq);
    }

    @Override
    public List<FAQEntity> getAllActiveFAQs() {
        return faqRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    @Override
    public FAQEntity getFAQById(Long id) {
        return faqRepository.findById(id).orElse(null);
    }

    @Override
    public FAQEntity updateFAQ(Long id, FAQEntity faq) {
        FAQEntity existingFAQ = faqRepository.findById(id).orElse(null);
        if (existingFAQ != null) {
            existingFAQ.setQuestion(faq.getQuestion());
            existingFAQ.setAnswer(faq.getAnswer());
            existingFAQ.setCategory(faq.getCategory());
            existingFAQ.setDisplayOrder(faq.getDisplayOrder());
            existingFAQ.setIsActive(faq.getIsActive());
            return faqRepository.save(existingFAQ);
        }
        return null;
    }

    @Override
    public void deleteFAQ(Long id) {
        FAQEntity existingFAQ = faqRepository.findById(id).orElse(null);
        if (existingFAQ != null) {
            existingFAQ.setIsActive(false);
            faqRepository.save(existingFAQ);
        }
    }

    @Override
    public List<FAQEntity> getFAQsByCategory(String category) {
        return faqRepository.findByCategoryAndIsActiveTrueOrderByDisplayOrderAsc(category);
    }

    @Override
    public List<FAQEntity> searchFAQs(String keyword) {
        return faqRepository.searchFAQsByKeyword(keyword);
    }

    @Override
    public List<String> getAllActiveCategories() {
        return faqRepository.findAllActiveCategories();
    }

    @Override
    public List<FAQEntity> getFAQsByCategories(List<String> categories) {
        return faqRepository.findByCategoriesAndIsActiveTrue(categories);
    }

    @Override
    public void reorderFAQs(List<Long> faqIds) {
        for (int i = 0; i < faqIds.size(); i++) {
            FAQEntity faq = faqRepository.findById(faqIds.get(i)).orElse(null);
            if (faq != null) {
                faq.setDisplayOrder(i + 1);
                faqRepository.save(faq);
            }
        }
    }

    @Override
    public FAQEntity toggleFAQStatus(Long id) {
        FAQEntity existingFAQ = faqRepository.findById(id).orElse(null);
        if (existingFAQ != null) {
            existingFAQ.setIsActive(!existingFAQ.getIsActive());
            return faqRepository.save(existingFAQ);
        }
        return null;
    }
}