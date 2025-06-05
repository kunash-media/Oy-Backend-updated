package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.CheckOutRequestDTO;
import com.oy.oy_jewels.dto.response.CheckOutResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CheckOutService {

    CheckOutResponseDTO createCheckOut(CheckOutRequestDTO checkOutRequestDTO);

    List<CheckOutResponseDTO> getAllCheckOuts();

    Optional<CheckOutResponseDTO> getCheckOutById(Long id);

    CheckOutResponseDTO updateCheckOut(Long id, CheckOutRequestDTO checkOutRequestDTO);

    void deleteCheckOut(Long id);

    List<CheckOutResponseDTO> getCheckOutsByEmail(String email);

    List<CheckOutResponseDTO> getCheckOutsByPhoneNumber(String phoneNumber);

    List<CheckOutResponseDTO> getCheckOutsByCity(String city);

    List<CheckOutResponseDTO> getCheckOutsByCountry(String country);
}