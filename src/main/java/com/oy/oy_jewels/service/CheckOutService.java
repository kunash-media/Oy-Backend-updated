package com.oy.oy_jewels.service;


import com.oy.oy_jewels.entity.CheckOutEntity;

import java.util.List;
import java.util.Optional;

public interface CheckOutService {

    CheckOutEntity createCheckOut(CheckOutEntity checkOut);

    List<CheckOutEntity> getAllCheckOuts();

    Optional<CheckOutEntity> getCheckOutById(Long id);

    CheckOutEntity updateCheckOut(Long id, CheckOutEntity checkOut);

    void deleteCheckOut(Long id);

    List<CheckOutEntity> getCheckOutsByEmail(String email);

    List<CheckOutEntity> getCheckOutsByPhoneNumber(String phoneNumber);

    List<CheckOutEntity> getCheckOutsByCity(String city);

    List<CheckOutEntity> getCheckOutsByCountry(String country);
}
