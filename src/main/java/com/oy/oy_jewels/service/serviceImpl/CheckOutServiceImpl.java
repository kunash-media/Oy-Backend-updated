package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.CheckOutEntity;
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

    @Override
    public CheckOutEntity createCheckOut(CheckOutEntity checkOut) {
        return checkOutRepository.save(checkOut);
    }

    @Override
    public List<CheckOutEntity> getAllCheckOuts() {
        return checkOutRepository.findAll();
    }

    @Override
    public Optional<CheckOutEntity> getCheckOutById(Long id) {
        return checkOutRepository.findById(id);
    }

    @Override
    public CheckOutEntity updateCheckOut(Long id, CheckOutEntity checkOut) {
        Optional<CheckOutEntity> existingCheckOut = checkOutRepository.findById(id);
        if (existingCheckOut.isPresent()) {
            CheckOutEntity entity = existingCheckOut.get();
            entity.setFullName(checkOut.getFullName());
            entity.setEmail(checkOut.getEmail());
            entity.setPhoneNumber(checkOut.getPhoneNumber());
            entity.setAlternatePhoneNumber(checkOut.getAlternatePhoneNumber());
            entity.setStreetAddress(checkOut.getStreetAddress());
            entity.setCity(checkOut.getCity());
            entity.setCountry(checkOut.getCountry());
            entity.setZipPostalCode(checkOut.getZipPostalCode());
            entity.setOrderSummaryTitle(checkOut.getOrderSummaryTitle());
            entity.setCouponCode(checkOut.getCouponCode());
            entity.setSubtotalLabel(checkOut.getSubtotalLabel());
            entity.setShippingLabel(checkOut.getShippingLabel());
            entity.setTaxLabel(checkOut.getTaxLabel());
            entity.setTotalCostLabel(checkOut.getTotalCostLabel());
            return checkOutRepository.save(entity);
        }
        return null;
    }

    @Override
    public void deleteCheckOut(Long id) {
        checkOutRepository.deleteById(id);
    }

    @Override
    public List<CheckOutEntity> getCheckOutsByEmail(String email) {
        return checkOutRepository.findByEmail(email);
    }

    @Override
    public List<CheckOutEntity> getCheckOutsByPhoneNumber(String phoneNumber) {
        return checkOutRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<CheckOutEntity> getCheckOutsByCity(String city) {
        return checkOutRepository.findByCity(city);
    }

    @Override
    public List<CheckOutEntity> getCheckOutsByCountry(String country) {
        return checkOutRepository.findByCountry(country);
    }
}