package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.CancellationEntity;
import com.oy.oy_jewels.repository.CancellationRepository;
import com.oy.oy_jewels.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancellationServiceImpl implements CancellationService {

    @Autowired
    private CancellationRepository cancellationRepository;

    @Override
    public CancellationEntity createCancellationPolicy(CancellationEntity cancellationEntity) {
        return cancellationRepository.save(cancellationEntity);
    }

    @Override
    public List<CancellationEntity> getAllCancellationPolicies() {
        return cancellationRepository.findAll();
    }

    @Override
    public CancellationEntity getCancellationPolicyById(Long id) {
        return cancellationRepository.findById(id).orElse(null);
    }

    @Override
    public CancellationEntity updateCancellationPolicy(Long id, CancellationEntity cancellationEntity) {
        CancellationEntity existingPolicy = cancellationRepository.findById(id).orElse(null);
        if (existingPolicy != null) {
            existingPolicy.setCancellationTitle(cancellationEntity.getCancellationTitle());
            existingPolicy.setCancellationDescription(cancellationEntity.getCancellationDescription());
            return cancellationRepository.save(existingPolicy);
        }
        return null;
    }

    @Override
    public void deleteCancellationPolicy(Long id) {
        cancellationRepository.deleteById(id);
    }

    @Override
    public List<CancellationEntity> searchByTitle(String title) {
        return cancellationRepository.findByCancellationTitleContainingIgnoreCase(title);
    }
}
