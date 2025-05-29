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
    public FAQEntity createFAQ(FAQEntity faqEntity) {
        return faqRepository.save(faqEntity);
    }

    @Override
    public FAQEntity getFAQById(Long id) {
        return faqRepository.findById(id).orElse(null);
    }

    @Override
    public List<FAQEntity> getAllFAQs() {
        return faqRepository.findAll();
    }

    @Override
    public FAQEntity updateFAQ(Long id, FAQEntity faqEntity) {
        FAQEntity existingFAQ = faqRepository.findById(id).orElse(null);

        if (existingFAQ == null) {
            return null;
        }

        if (faqEntity.getFaq1Title() != null) {
            existingFAQ.setFaq1Title(faqEntity.getFaq1Title());
        }

        if (faqEntity.getFaq1Description() != null) {
            existingFAQ.setFaq1Description(faqEntity.getFaq1Description());
        }

        if (faqEntity.getFaq2Title() != null) {
            existingFAQ.setFaq2Title(faqEntity.getFaq2Title());
        }

        if (faqEntity.getFaq2Description() != null) {
            existingFAQ.setFaq2Description(faqEntity.getFaq2Description());
        }

        if (faqEntity.getFaq3Title() != null) {
            existingFAQ.setFaq3Title(faqEntity.getFaq3Title());
        }

        if (faqEntity.getFaq3Description() != null) {
            existingFAQ.setFaq3Description(faqEntity.getFaq3Description());
        }

        if (faqEntity.getFaq4Title() != null) {
            existingFAQ.setFaq4Title(faqEntity.getFaq4Title());
        }

        if (faqEntity.getFaq4Description() != null) {
            existingFAQ.setFaq4Description(faqEntity.getFaq4Description());
        }

        if (faqEntity.getFaq5Title() != null) {
            existingFAQ.setFaq5Title(faqEntity.getFaq5Title());
        }

        if (faqEntity.getFaq5Description() != null) {
            existingFAQ.setFaq5Description(faqEntity.getFaq5Description());
        }

        if (faqEntity.getFaq6Title() != null) {
            existingFAQ.setFaq6Title(faqEntity.getFaq6Title());
        }

        if (faqEntity.getFaq6Description() != null) {
            existingFAQ.setFaq6Description(faqEntity.getFaq6Description());
        }

        return faqRepository.save(existingFAQ);
    }

    @Override
    public void deleteFAQ(Long id) {
        faqRepository.deleteById(id);
    }
}
