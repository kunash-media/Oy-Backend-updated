package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.TermsEntity;
import com.oy.oy_jewels.repository.TermsRepository;
import com.oy.oy_jewels.service.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermsServiceImpl implements TermsService {

    @Autowired
    private TermsRepository termsRepository;

    @Override
    public TermsEntity createTermsPolicy(TermsEntity termsEntity) {
        return termsRepository.save(termsEntity);
    }

    @Override
    public List<TermsEntity> getAllTermsPolicies() {
        return termsRepository.findAll();
    }

    @Override
    public TermsEntity getTermsPolicyById(Long id) {
        return termsRepository.findById(id).orElse(null);
    }

    @Override
    public TermsEntity updateTermsPolicy(Long id, TermsEntity termsEntity) {
        TermsEntity existingPolicy = termsRepository.findById(id).orElse(null);
        if (existingPolicy != null) {
            existingPolicy.setTermsTitle(termsEntity.getTermsTitle());
            existingPolicy.setTermsDescription(termsEntity.getTermsDescription());
            return termsRepository.save(existingPolicy);
        }
        return null;
    }

    @Override
    public void deleteTermsPolicy(Long id) {
        termsRepository.deleteById(id);
    }

    @Override
    public List<TermsEntity> searchByTitle(String title) {
        return termsRepository.findByTermsTitleContainingIgnoreCase(title);
    }
}
