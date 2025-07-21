package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.ShippingAddressDTO;
import com.oy.oy_jewels.entity.ShippingAddressEntity;
import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.ShippingAddressRepository;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.ShippingAddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

    private final ShippingAddressRepository addressRepository;
    private final UserRepository userRepository;

    public ShippingAddressServiceImpl(ShippingAddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ShippingAddressDTO createAddress(Long userId, ShippingAddressDTO addressDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShippingAddressEntity addressEntity = new ShippingAddressEntity();
        BeanUtils.copyProperties(addressDTO, addressEntity);
        addressEntity.setUser(user);

        addressEntity = addressRepository.save(addressEntity);

        addressDTO.setShippingId(addressEntity.getShippingId());
        return addressDTO;
    }

    @Override
    public List<ShippingAddressDTO> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserUserId(userId).stream()
                .map(addressEntity -> {
                    ShippingAddressDTO dto = new ShippingAddressDTO();
                    BeanUtils.copyProperties(addressEntity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ShippingAddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressEntity -> {
                    ShippingAddressDTO dto = new ShippingAddressDTO();
                    BeanUtils.copyProperties(addressEntity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ShippingAddressDTO updateAddress(Long userId, Long shippingId, ShippingAddressDTO addressDTO) {
        ShippingAddressEntity addressEntity = addressRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!addressEntity.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        // Update only non-null fields from the DTO
        if (addressDTO.getCustomerPhone() != null) {
            addressEntity.setCustomerPhone(addressDTO.getCustomerPhone());
        }
        if (addressDTO.getCustomerEmail() != null) {
            addressEntity.setCustomerEmail(addressDTO.getCustomerEmail());
        }
        if (addressDTO.getShippingAddress() != null) {
            addressEntity.setShippingAddress(addressDTO.getShippingAddress());
        }
        if (addressDTO.getShippingCity() != null) {
            addressEntity.setShippingCity(addressDTO.getShippingCity());
        }
        if (addressDTO.getShippingState() != null) {
            addressEntity.setShippingState(addressDTO.getShippingState());
        }
        if (addressDTO.getShippingPincode() != null) {
            addressEntity.setShippingPincode(addressDTO.getShippingPincode());
        }
        if (addressDTO.getShippingCountry() != null) {
            addressEntity.setShippingCountry(addressDTO.getShippingCountry());
        }

        addressEntity = addressRepository.save(addressEntity);

        ShippingAddressDTO updatedDTO = new ShippingAddressDTO();
        BeanUtils.copyProperties(addressEntity, updatedDTO);
        return updatedDTO;
    }


    @Override
    public void deleteAddress(Long userId, Long shippingId) {
        ShippingAddressEntity addressEntity = addressRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!addressEntity.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        addressRepository.delete(addressEntity);
    }
}