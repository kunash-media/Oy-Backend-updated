package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.DisclaimerEntity;
import com.oy.oy_jewels.repository.DisclaimerRepository;
import com.oy.oy_jewels.service.DisclaimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisclaimerServiceImpl implements DisclaimerService {

    @Autowired
    private DisclaimerRepository disclaimerRepository;

    @Override
    public DisclaimerEntity createDisclaimer(DisclaimerEntity disclaimerEntity) {
        return disclaimerRepository.save(disclaimerEntity);
    }

    @Override
    public List<DisclaimerEntity> getAllDisclaimers() {
        return disclaimerRepository.findAll();
    }

    @Override
    public DisclaimerEntity getDisclaimerById(Long id) {
        return disclaimerRepository.findById(id).orElse(null);
    }

    @Override
    public DisclaimerEntity updateDisclaimer(Long id, DisclaimerEntity disclaimerEntity) {
        DisclaimerEntity existingDisclaimer = disclaimerRepository.findById(id).orElse(null);
        if (existingDisclaimer != null) {
            existingDisclaimer.setDisclaimerTitle(disclaimerEntity.getDisclaimerTitle());
            existingDisclaimer.setDisclaimerDescription(disclaimerEntity.getDisclaimerDescription());
            existingDisclaimer.setSectionNumber(disclaimerEntity.getSectionNumber());
            return disclaimerRepository.save(existingDisclaimer);
        }
        return null;
    }

    @Override
    public void deleteDisclaimer(Long id) {
        disclaimerRepository.deleteById(id);
    }

    @Override
    public List<DisclaimerEntity> searchByTitle(String title) {
        return disclaimerRepository.findByDisclaimerTitleContainingIgnoreCase(title);
    }

    @Override
    public DisclaimerEntity getDisclaimerBySectionNumber(Integer sectionNumber) {
        return disclaimerRepository.findBySectionNumber(sectionNumber);
    }

    @Override
    public List<DisclaimerEntity> getAllDisclaimersOrdered() {
        return disclaimerRepository.findAllByOrderBySectionNumberAsc();
    }

    @Override
    public List<DisclaimerEntity> getDisclaimersWithoutTitles() {
        return disclaimerRepository.findByDisclaimerTitleIsNull();
    }
}
