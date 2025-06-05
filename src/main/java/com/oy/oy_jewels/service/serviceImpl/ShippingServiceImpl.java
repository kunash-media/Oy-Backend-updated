package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.ShippingRequestDTO;
import com.oy.oy_jewels.dto.response.ShippingResponseDTO;
import com.oy.oy_jewels.entity.ShippingEntity;
import com.oy.oy_jewels.repository.ShippingRepository;
import com.oy.oy_jewels.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public ShippingResponseDTO createShipping(ShippingRequestDTO shippingRequest) {
        ShippingEntity shippingEntity = convertToEntity(shippingRequest);
        ShippingEntity savedEntity = shippingRepository.save(shippingEntity);
        return convertToResponseDTO(savedEntity);
    }

    @Override
    public List<ShippingResponseDTO> getAllShippings() {
        List<ShippingEntity> shippingEntities = shippingRepository.findAll();
        return shippingEntities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShippingResponseDTO getShippingById(Long id) {
        Optional<ShippingEntity> shippingEntity = shippingRepository.findById(id);
        return shippingEntity.map(this::convertToResponseDTO).orElse(null);
    }

    @Override
    public ShippingResponseDTO updateShipping(Long id, ShippingRequestDTO shippingRequest) {
        Optional<ShippingEntity> existingShipping = shippingRepository.findById(id);
        if (existingShipping.isPresent()) {
            ShippingEntity entity = existingShipping.get();
            updateEntityFromRequest(entity, shippingRequest);
            ShippingEntity updatedEntity = shippingRepository.save(entity);
            return convertToResponseDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteShipping(Long id) {
        shippingRepository.deleteById(id);
    }

    @Override
    public List<ShippingResponseDTO> searchByTitle(String title) {
        List<ShippingEntity> shippingEntities = shippingRepository.findByShippingTitleContaining(title);
        return shippingEntities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShippingResponseDTO> searchByDescription(String description) {
        List<ShippingEntity> shippingEntities = shippingRepository.findByShippingDescriptionContaining(description);
        return shippingEntities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ShippingEntity convertToEntity(ShippingRequestDTO shippingRequest) {
        return new ShippingEntity(
                shippingRequest.getShippingTitle1(),
                shippingRequest.getShippingDescription1(),
                shippingRequest.getShippingTitle2(),
                shippingRequest.getShippingDescription2(),
                shippingRequest.getShippingTitle3(),
                shippingRequest.getShippingDescription3(),
                shippingRequest.getShippingTitle4(),
                shippingRequest.getShippingDescription4(),
                shippingRequest.getShippingTitle5(),
                shippingRequest.getShippingDescription5()
        );
    }

    private void updateEntityFromRequest(ShippingEntity entity, ShippingRequestDTO shippingRequest) {
        entity.setShippingTitle1(shippingRequest.getShippingTitle1());
        entity.setShippingDescription1(shippingRequest.getShippingDescription1());
        entity.setShippingTitle2(shippingRequest.getShippingTitle2());
        entity.setShippingDescription2(shippingRequest.getShippingDescription2());
        entity.setShippingTitle3(shippingRequest.getShippingTitle3());
        entity.setShippingDescription3(shippingRequest.getShippingDescription3());
        entity.setShippingTitle4(shippingRequest.getShippingTitle4());
        entity.setShippingDescription4(shippingRequest.getShippingDescription4());
        entity.setShippingTitle5(shippingRequest.getShippingTitle5());
        entity.setShippingDescription5(shippingRequest.getShippingDescription5());
    }

    private ShippingResponseDTO convertToResponseDTO(ShippingEntity shippingEntity) {
        return new ShippingResponseDTO(
                shippingEntity.getId(),
                shippingEntity.getShippingTitle1(),
                shippingEntity.getShippingDescription1(),
                shippingEntity.getShippingTitle2(),
                shippingEntity.getShippingDescription2(),
                shippingEntity.getShippingTitle3(),
                shippingEntity.getShippingDescription3(),
                shippingEntity.getShippingTitle4(),
                shippingEntity.getShippingDescription4(),
                shippingEntity.getShippingTitle5(),
                shippingEntity.getShippingDescription5()
        );
    }
}