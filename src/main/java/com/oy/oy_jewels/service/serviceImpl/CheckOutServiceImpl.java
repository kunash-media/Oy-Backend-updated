package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.dto.request.CheckOutRequestDTO;
import com.oy.oy_jewels.dto.response.CheckOutResponseDTO;
import com.oy.oy_jewels.entity.CheckOutEntity;
import com.oy.oy_jewels.mapper.CheckOutMapper;
import com.oy.oy_jewels.repository.CheckOutRepository;
import com.oy.oy_jewels.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckOutServiceImpl implements CheckOutService {

    @Autowired
    private CheckOutRepository checkOutRepository;

    @Autowired
    private CheckOutMapper checkOutMapper;

    @Override
    public CheckOutResponseDTO createCheckOut(CheckOutRequestDTO checkOutRequestDTO) {
        CheckOutEntity entity = checkOutMapper.toEntity(checkOutRequestDTO);
        CheckOutEntity savedEntity = checkOutRepository.save(entity);
        return checkOutMapper.toResponseDTO(savedEntity);
    }

    @Override
    public List<CheckOutResponseDTO> getAllCheckOuts() {
        List<CheckOutEntity> entities = checkOutRepository.findAll();
        return checkOutMapper.toResponseDTOList(entities);
    }

    @Override
    public Optional<CheckOutResponseDTO> getCheckOutById(Long id) {
        Optional<CheckOutEntity> entity = checkOutRepository.findById(id);
        return entity.map(checkOutMapper::toResponseDTO);
    }

    @Override
    public CheckOutResponseDTO updateCheckOut(Long id, CheckOutRequestDTO checkOutRequestDTO) {
        Optional<CheckOutEntity> existingCheckOut = checkOutRepository.findById(id);
        if (existingCheckOut.isPresent()) {
            CheckOutEntity entity = existingCheckOut.get();
            checkOutMapper.updateEntityFromRequestDTO(entity, checkOutRequestDTO);
            CheckOutEntity updatedEntity = checkOutRepository.save(entity);
            return checkOutMapper.toResponseDTO(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteCheckOut(Long id) {
        checkOutRepository.deleteById(id);
    }

    @Override
    public List<CheckOutResponseDTO> getCheckOutsByEmail(String email) {
        List<CheckOutEntity> entities = checkOutRepository.findByEmail(email);
        return checkOutMapper.toResponseDTOList(entities);
    }

    @Override
    public List<CheckOutResponseDTO> getCheckOutsByPhoneNumber(String phoneNumber) {
        List<CheckOutEntity> entities = checkOutRepository.findByPhoneNumber(phoneNumber);
        return checkOutMapper.toResponseDTOList(entities);
    }

    @Override
    public List<CheckOutResponseDTO> getCheckOutsByCity(String city) {
        List<CheckOutEntity> entities = checkOutRepository.findByCity(city);
        return checkOutMapper.toResponseDTOList(entities);
    }

    @Override
    public List<CheckOutResponseDTO> getCheckOutsByCountry(String country) {
        List<CheckOutEntity> entities = checkOutRepository.findByCountry(country);
        return checkOutMapper.toResponseDTOList(entities);
    }
}
