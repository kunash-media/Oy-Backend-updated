package com.oy.oy_jewels.service;


import com.oy.oy_jewels.entity.UserEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    //login api
    Optional<UserEntity> authenticateUserByMobileAndPassword(String mobile, String password);

    // Create new user
    UserEntity createUser(UserEntity user);

    // Get all users
    List<UserEntity> getAllUsers();

    // Get user by ID
    UserEntity getUserById(Long userId);

    // Update user
    UserEntity updateUser(Long userId, UserEntity user);

    // Delete user
    void deleteUser(Long userId);

    // Get users by status
    List<UserEntity> getUsersByStatus(String status);

    // Get user by email
    UserEntity getUserByEmail(String email);

    UserEntity patchUser(Long userId, Map<String, Object> updates);

    boolean changePassword(Long userId, String oldPassword, String newPassword);

}
