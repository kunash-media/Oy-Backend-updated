package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.entity.OurStaffEntity;
import com.oy.oy_jewels.repository.OurStaffRepository;
import com.oy.oy_jewels.service.OurStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OurStaffServiceImpl implements OurStaffService {

    @Autowired
    private OurStaffRepository staffRepository;

    private final String uploadDir = "uploads/staff-images/";

    @Override
    public OurStaffEntity createStaff(OurStaffEntity staff, MultipartFile staffImage) {
        // Handle image upload - convert to byte array
        if (staffImage != null && !staffImage.isEmpty()) {
            try {
                byte[] imageBytes = staffImage.getBytes();
                staff.setStaffImage(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Error processing image file", e);
            }
        }

        // Set joining date if not provided
        if (staff.getJoiningDate() == null) {
            staff.setJoiningDate(LocalDate.now());
        }

        return staffRepository.save(staff);
    }

    @Override
    public List<OurStaffEntity> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<OurStaffEntity> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Optional<OurStaffEntity> getStaffByEmail(String emailAddress) {
        return staffRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public List<OurStaffEntity> getStaffByRole(String staffRole) {
        return staffRepository.findByStaffRole(staffRole);
    }

    @Override
    public List<OurStaffEntity> searchStaffByName(String staffName) {
        return staffRepository.findByStaffNameContainingIgnoreCase(staffName);
    }

    @Override
    public OurStaffEntity updateStaff(Long id, OurStaffEntity updatedStaff, MultipartFile staffImage) {
        Optional<OurStaffEntity> existingStaffOpt = staffRepository.findById(id);

        if (existingStaffOpt.isPresent()) {
            OurStaffEntity existingStaff = existingStaffOpt.get();

            // Update fields
            existingStaff.setStaffName(updatedStaff.getStaffName());
            existingStaff.setEmailAddress(updatedStaff.getEmailAddress());
            existingStaff.setPassword(updatedStaff.getPassword());
            existingStaff.setContactNumber(updatedStaff.getContactNumber());
            existingStaff.setJoiningDate(updatedStaff.getJoiningDate());
            existingStaff.setStaffRole(updatedStaff.getStaffRole());

            // Handle image update
            if (staffImage != null && !staffImage.isEmpty()) {
                try {
                    byte[] imageBytes = staffImage.getBytes();
                    existingStaff.setStaffImage(imageBytes);
                } catch (IOException e) {
                    throw new RuntimeException("Error processing image file", e);
                }
            }

            return staffRepository.save(existingStaff);
        }

        return null;
    }

    @Override
    public boolean deleteStaff(Long id) {
        if (staffRepository.existsById(id)) {
            staffRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean emailExists(String emailAddress) {
        return staffRepository.existsByEmailAddress(emailAddress);
    }

    @Override
    public List<OurStaffEntity> getStaffOrderedByJoiningDate() {
        return staffRepository.findAllByOrderByJoiningDateDesc();
    }

    @Override
    public List<OurStaffEntity> getStaffByRoleOrderedByJoiningDate(String role) {
        return staffRepository.findStaffByRoleOrderedByJoiningDate(role);
    }

    // Helper method to save image (kept for future use if needed)
    private String saveImage(MultipartFile image) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return uploadDir + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }
}