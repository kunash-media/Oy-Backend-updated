package com.oy.oy_jewels.service.serviceImpl;


import com.oy.oy_jewels.bcrypt.BcryptEncoderConfig;
import com.oy.oy_jewels.dto.request.StaffUpdateRequest;
import com.oy.oy_jewels.entity.OurStaffEntity;
import com.oy.oy_jewels.repository.OurStaffRepository;
import com.oy.oy_jewels.service.OurStaffService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OurStaffServiceImpl implements OurStaffService {

    @Autowired
    private OurStaffRepository staffRepository;

    @Autowired
    private final BcryptEncoderConfig passwordEncoder;

    public OurStaffServiceImpl(OurStaffRepository staffRepository, BcryptEncoderConfig passwordEncoder){
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        String encodedPassword = passwordEncoder.encode(staff.getPassword());
        staff.setPassword(encodedPassword);

        return staffRepository.save(staff);
    }


    @Override
    public List<OurStaffEntity> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public OurStaffEntity getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
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
    @Transactional
    public OurStaffEntity updateStaff(Long id, StaffUpdateRequest request, MultipartFile staffImage) throws IOException {
        OurStaffEntity existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found"));

        // Update fields from the DTO request - only update if values are provided
        if (request.getStaffName() != null && !request.getStaffName().trim().isEmpty()) {
            existingStaff.setStaffName(request.getStaffName());
        }

        if (request.getEmailAddress() != null && !request.getEmailAddress().trim().isEmpty()) {
            existingStaff.setEmailAddress(request.getEmailAddress());
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            // Consider encrypting the password before saving
            existingStaff.setPassword(request.getPassword());
        }

        if (request.getContactNumber() != null && !request.getContactNumber().trim().isEmpty()) {
            existingStaff.setContactNumber(request.getContactNumber());
        }

        if (request.getJoiningDate() != null && !request.getJoiningDate().trim().isEmpty()) {
            // Parse string date to LocalDate
            try {
                LocalDate joiningDate = LocalDate.parse(request.getJoiningDate());
                existingStaff.setJoiningDate(joiningDate);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
            }
        }

        if (request.getStaffRole() != null && !request.getStaffRole().trim().isEmpty()) {
            existingStaff.setStaffRole(request.getStaffRole());
        }

        // Handle image update - convert MultipartFile to byte[]
        if (staffImage != null && !staffImage.isEmpty()) {
            existingStaff.setStaffImage(staffImage.getBytes());
        }

        return staffRepository.save(existingStaff);
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