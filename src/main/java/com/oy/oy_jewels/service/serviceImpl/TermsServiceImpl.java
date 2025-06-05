package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.TermsRequestDTO;
import com.oy.oy_jewels.dto.response.TermsResponseDTO;
import com.oy.oy_jewels.entity.TermsEntity;
import com.oy.oy_jewels.repository.TermsRepository;
import com.oy.oy_jewels.service.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TermsServiceImpl implements TermsService {

    @Autowired
    private TermsRepository termsRepository;

    @Override
    public TermsResponseDTO createTermsPolicy(TermsRequestDTO termsRequestDTO) {
        TermsEntity termsEntity = convertToEntity(termsRequestDTO);
        TermsEntity savedEntity = termsRepository.save(termsEntity);
        return convertToResponseDTO(savedEntity);
    }

    @Override
    public List<TermsResponseDTO> getAllTermsPolicies() {
        List<TermsEntity> entities = termsRepository.findAll();
        return entities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TermsResponseDTO getTermsPolicyById(Long id) {
        TermsEntity entity = termsRepository.findById(id).orElse(null);
        return entity != null ? convertToResponseDTO(entity) : null;
    }

    @Override
    public TermsResponseDTO updateTermsPolicy(Long id, TermsRequestDTO termsRequestDTO) {
        TermsEntity existingPolicy = termsRepository.findById(id).orElse(null);
        if (existingPolicy != null) {
            existingPolicy.setTermsTitle(termsRequestDTO.getTermsTitle());
            existingPolicy.setTermsDescription(termsRequestDTO.getTermsDescription());
            TermsEntity updatedEntity = termsRepository.save(existingPolicy);
            return convertToResponseDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteTermsPolicy(Long id) {
        termsRepository.deleteById(id);
    }

    @Override
    public List<TermsResponseDTO> searchByTitle(String title) {
        List<TermsEntity> entities = termsRepository.findByTermsTitleContainingIgnoreCase(title);
        return entities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert DTO to Entity
    private TermsEntity convertToEntity(TermsRequestDTO dto) {
        TermsEntity entity = new TermsEntity();
        entity.setTermsTitle(dto.getTermsTitle());
        entity.setTermsDescription(dto.getTermsDescription());
        return entity;
    }

    // Helper method to convert Entity to Response DTO
    private TermsResponseDTO convertToResponseDTO(TermsEntity entity) {
        TermsResponseDTO dto = new TermsResponseDTO();
        dto.setId(entity.getId());
        dto.setTermsTitle(entity.getTermsTitle());
        dto.setTermsDescription(entity.getTermsDescription());
        return dto;
    }
}