package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.FAQRequestDTO;
import com.oy.oy_jewels.dto.response.FAQResponseDTO;
import com.oy.oy_jewels.entity.FAQEntity;
import com.oy.oy_jewels.mapper.FAQMapper;
import com.oy.oy_jewels.repository.FAQRepository;
import com.oy.oy_jewels.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private FAQMapper faqMapper;

    @Override
    public FAQResponseDTO createFAQ(FAQRequestDTO requestDTO) {
        FAQEntity entity = faqMapper.requestDtoToEntity(requestDTO);
        FAQEntity savedEntity = faqRepository.save(entity);
        return faqMapper.entityToResponseDto(savedEntity);
    }

    @Override
    public FAQResponseDTO getFAQById(Long id) {
        FAQEntity entity = faqRepository.findById(id).orElse(null);
        return faqMapper.entityToResponseDto(entity);
    }

    @Override
    public List<FAQResponseDTO> getAllFAQs() {
        List<FAQEntity> entities = faqRepository.findAll();
        return faqMapper.entityListToResponseDtoList(entities);
    }

    @Override
    public FAQResponseDTO updateFAQ(Long id, FAQRequestDTO requestDTO) {
        FAQEntity existingEntity = faqRepository.findById(id).orElse(null);

        if (existingEntity == null) {
            return null;
        }

        // Use the mapper's update method which maintains the existing null-check logic
        faqMapper.updateEntityFromRequestDto(existingEntity, requestDTO);

        FAQEntity updatedEntity = faqRepository.save(existingEntity);
        return faqMapper.entityToResponseDto(updatedEntity);
    }

    @Override
    public void deleteFAQ(Long id) {
        faqRepository.deleteById(id);
    }
}