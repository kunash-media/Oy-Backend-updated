package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.CancellationRequestDTO;
import com.oy.oy_jewels.dto.response.CancellationResponseDTO;
import com.oy.oy_jewels.entity.CancellationEntity;
import com.oy.oy_jewels.mapper.CancellationMapper;
import com.oy.oy_jewels.repository.CancellationRepository;
import com.oy.oy_jewels.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancellationServiceImpl implements CancellationService {

    @Autowired
    private CancellationRepository cancellationRepository;

    @Autowired
    private CancellationMapper cancellationMapper;

    @Override
    public CancellationResponseDTO createCancellationPolicy(CancellationRequestDTO cancellationRequestDTO) {
        CancellationEntity entity = cancellationMapper.toEntity(cancellationRequestDTO);
        CancellationEntity savedEntity = cancellationRepository.save(entity);
        return cancellationMapper.toResponseDTO(savedEntity);
    }

    @Override
    public List<CancellationResponseDTO> getAllCancellationPolicies() {
        List<CancellationEntity> entities = cancellationRepository.findAll();
        return cancellationMapper.toResponseDTOList(entities);
    }

    @Override
    public CancellationResponseDTO getCancellationPolicyById(Long id) {
        CancellationEntity entity = cancellationRepository.findById(id).orElse(null);
        return cancellationMapper.toResponseDTO(entity);
    }

    @Override
    public CancellationResponseDTO updateCancellationPolicy(Long id, CancellationRequestDTO cancellationRequestDTO) {
        CancellationEntity existingEntity = cancellationRepository.findById(id).orElse(null);
        if (existingEntity != null) {
            cancellationMapper.updateEntityFromDTO(cancellationRequestDTO, existingEntity);
            CancellationEntity updatedEntity = cancellationRepository.save(existingEntity);
            return cancellationMapper.toResponseDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteCancellationPolicy(Long id) {
        cancellationRepository.deleteById(id);
    }

    @Override
    public List<CancellationResponseDTO> searchByTitle(String title) {
        List<CancellationEntity> entities = cancellationRepository.findByCancellationTitleContainingIgnoreCase(title);
        return cancellationMapper.toResponseDTOList(entities);
    }
}