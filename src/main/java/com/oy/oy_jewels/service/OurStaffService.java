package com.oy.oy_jewels.service;


import com.oy.oy_jewels.entity.OurStaffEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface OurStaffService {

    // Create new staff
    OurStaffEntity createStaff(OurStaffEntity staff, MultipartFile staffImage);

    // Get all staff members
    List<OurStaffEntity> getAllStaff();

    // Get staff by ID
    Optional<OurStaffEntity> getStaffById(Long id);

    // Get staff by email
    Optional<OurStaffEntity> getStaffByEmail(String emailAddress);

    // Get staff by role
    List<OurStaffEntity> getStaffByRole(String staffRole);

    // Search staff by name
    List<OurStaffEntity> searchStaffByName(String staffName);

    // Update staff
    OurStaffEntity updateStaff(Long id, OurStaffEntity staff, MultipartFile staffImage);

    // Delete staff
    boolean deleteStaff(Long id);

    // Check if email exists
    boolean emailExists(String emailAddress);

    // Get staff ordered by joining date
    List<OurStaffEntity> getStaffOrderedByJoiningDate();

    // Get staff by role ordered by joining date
    List<OurStaffEntity> getStaffByRoleOrderedByJoiningDate(String role);
}
