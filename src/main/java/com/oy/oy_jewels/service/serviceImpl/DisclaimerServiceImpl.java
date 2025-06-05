package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.DisclaimerRequestDTO;
import com.oy.oy_jewels.dto.response.DisclaimerResponseDTO;
import com.oy.oy_jewels.entity.DisclaimerEntity;
import com.oy.oy_jewels.mapper.DisclaimerMapper;
import com.oy.oy_jewels.repository.DisclaimerRepository;
import com.oy.oy_jewels.service.DisclaimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisclaimerServiceImpl implements DisclaimerService {

    @Autowired
    private DisclaimerRepository disclaimerRepository;

    @Autowired
    private DisclaimerMapper disclaimerMapper;

    @Override
    public DisclaimerResponseDTO createDisclaimer(DisclaimerRequestDTO requestDTO) {
        DisclaimerEntity entity = disclaimerMapper.requestDtoToEntity(requestDTO);
        DisclaimerEntity savedEntity = disclaimerRepository.save(entity);
        return disclaimerMapper.entityToResponseDto(savedEntity);
    }

    @Override
    public List<DisclaimerResponseDTO> getAllDisclaimers() {
        List<DisclaimerEntity> entities = disclaimerRepository.findAll();
        return disclaimerMapper.entityListToResponseDtoList(entities);
    }

    @Override
    public DisclaimerResponseDTO getDisclaimerById(Long id) {
        DisclaimerEntity entity = disclaimerRepository.findById(id).orElse(null);
        return disclaimerMapper.entityToResponseDto(entity);
    }

    @Override
    public DisclaimerResponseDTO updateDisclaimer(Long id, DisclaimerRequestDTO requestDTO) {
        DisclaimerEntity existingEntity = disclaimerRepository.findById(id).orElse(null);
        if (existingEntity != null) {
            disclaimerMapper.updateEntityFromRequestDto(existingEntity, requestDTO);
            DisclaimerEntity updatedEntity = disclaimerRepository.save(existingEntity);
            return disclaimerMapper.entityToResponseDto(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteDisclaimer(Long id) {
        disclaimerRepository.deleteById(id);
    }

    @Override
    public List<DisclaimerResponseDTO> searchByTitle(String title) {
        List<DisclaimerEntity> entities = disclaimerRepository.findByDisclaimerTitleContainingIgnoreCase(title);
        return disclaimerMapper.entityListToResponseDtoList(entities);
    }

    @Override
    public DisclaimerResponseDTO getDisclaimerBySectionNumber(Integer sectionNumber) {
        DisclaimerEntity entity = disclaimerRepository.findBySectionNumber(sectionNumber);
        return disclaimerMapper.entityToResponseDto(entity);
    }

    @Override
    public List<DisclaimerResponseDTO> getAllDisclaimersOrdered() {
        List<DisclaimerEntity> entities = disclaimerRepository.findAllByOrderBySectionNumberAsc();
        return disclaimerMapper.entityListToResponseDtoList(entities);
    }

    @Override
    public List<DisclaimerResponseDTO> getDisclaimersWithoutTitles() {
        List<DisclaimerEntity> entities = disclaimerRepository.findByDisclaimerTitleIsNull();
        return disclaimerMapper.entityListToResponseDtoList(entities);
    }
}
