package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.repository.UserRepository;
import com.oy.oy_jewels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        // Set default status if not provided
        if (user.getStatus() == null || user.getStatus().isEmpty()) {
            user.setStatus("active");
        }
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public UserEntity updateUser(Long userId, UserEntity user) {
        UserEntity existingUser = getUserById(userId);

        // Update all fields
        existingUser.setCustomerName(user.getCustomerName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());
        existingUser.setMaritalStatus(user.getMaritalStatus());
        existingUser.setCustomerDOB(user.getCustomerDOB());
        existingUser.setAnniversary(user.getAnniversary());
        existingUser.setStatus(user.getStatus());

        return userRepository.save(existingUser);
    }

    @Override
    public UserEntity patchUser(Long userId, Map<String, Object> updates) {
        UserEntity existingUser = getUserById(userId);

        // Apply partial updates - only update provided fields
        updates.forEach((key, value) -> {
            switch (key) {
                case "customerName":
                    existingUser.setCustomerName((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                case "mobile":
                    existingUser.setMobile((String) value);
                    break;
                case "maritalStatus":
                    existingUser.setMaritalStatus((String) value);
                    break;
                case "customerDOB":
                    existingUser.setCustomerDOB(LocalDate.parse(value.toString()));
                    break;
                case "anniversary":
                    existingUser.setAnniversary(LocalDate.parse(value.toString()));
                    break;
                case "status":
                    existingUser.setStatus((String) value);
                    break;
            }
        });

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity user = getUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public List<UserEntity> getUsersByStatus(String status) {
        return userRepository.findByStatus(status);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
