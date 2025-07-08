package com.oy.oy_jewels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oy.oy_jewels.dto.request.StaffCreateRequest;
import com.oy.oy_jewels.dto.request.StaffUpdateRequest;
import com.oy.oy_jewels.dto.response.ApiResponse;
import com.oy.oy_jewels.dto.response.StaffResponse;
import com.oy.oy_jewels.entity.OurStaffEntity;
import com.oy.oy_jewels.service.OurStaffService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
public class OurStaffController {

    @Autowired
    private OurStaffService staffService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(
            @RequestPart(value = "staffImage", required = false) MultipartFile staffImage,
            @RequestPart("staffData") StaffCreateRequest staffData) {

        try {
            OurStaffEntity staff = convertToEntity(staffData);
            OurStaffEntity createdStaff = staffService.createStaff(staff, staffImage);
            StaffResponse response = convertToResponse(createdStaff);

            return new ResponseEntity<>(ApiResponse.success("Staff created successfully", response), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error creating staff: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-staff")
    public ResponseEntity<List<OurStaffEntity>> getAllStaff() {
            List<OurStaffEntity> staffList = staffService.getAllStaff();
            return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @GetMapping("/get-staff/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        try {
            OurStaffEntity staff = staffService.getStaffById(id);
            return staff != null
                    ? ResponseEntity.ok(staff)
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error retrieving staff: " + e.getMessage());
        }
    }

    @GetMapping("/email/{emailAddress}")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffByEmail(@PathVariable String emailAddress) {
        try {
            Optional<OurStaffEntity> staff = staffService.getStaffByEmail(emailAddress);
            if (staff.isPresent()) {
                StaffResponse response = convertToResponse(staff.get());
                return new ResponseEntity<>(ApiResponse.success("Staff retrieved successfully", response), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("Staff not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getStaffByRole(@PathVariable String role) {
        try {
            List<OurStaffEntity> staffList = staffService.getStaffByRole(role);
            List<StaffResponse> responseList = staffList.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success("Staff retrieved successfully", responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving staff by role: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> searchStaffByName(@RequestParam String name) {
        try {
            List<OurStaffEntity> staffList = staffService.searchStaffByName(name);
            List<StaffResponse> responseList = staffList.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success("Staff search completed successfully", responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error searching staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaffAlternative(
            @PathVariable Long id,
            @RequestPart(value = "staffData", required = false) String staffDataJson,
            @RequestPart(value = "staffImage", required = false) MultipartFile staffImage) {
        try {
            StaffUpdateRequest request = new StaffUpdateRequest();

            // Parse JSON string if provided
            if (staffDataJson != null && !staffDataJson.trim().isEmpty()) {
                // You'll need to add Jackson ObjectMapper dependency
                ObjectMapper mapper = new ObjectMapper();
                request = mapper.readValue(staffDataJson, StaffUpdateRequest.class);
            }

            // Call service with DTO and MultipartFile
            OurStaffEntity updatedStaff = staffService.updateStaff(id, request, staffImage);
            StaffResponse response = convertToResponse(updatedStaff);

            return ResponseEntity.ok(
                    ApiResponse.success("Staff updated successfully", response)
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to process image"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        try {
            boolean deleted = staffService.deleteStaff(id);
            if (deleted) {
                return new ResponseEntity<>("Staff deleted successfully ", HttpStatus.OK);
            }
            return new ResponseEntity<>("Staff not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting staff: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-email/{emailAddress}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(@PathVariable String emailAddress) {
        try {
            boolean exists = staffService.emailExists(emailAddress);
            return new ResponseEntity<>(ApiResponse.success("Email check completed", exists), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error checking email: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ordered-by-joining-date")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getStaffOrderedByJoiningDate() {
        try {
            List<OurStaffEntity> staffList = staffService.getStaffOrderedByJoiningDate();
            List<StaffResponse> responseList = staffList.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success("Staff retrieved successfully", responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving ordered staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/role/{role}/ordered-by-joining-date")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getStaffByRoleOrderedByJoiningDate(@PathVariable String role) {
        try {
            List<OurStaffEntity> staffList = staffService.getStaffByRoleOrderedByJoiningDate(role);
            List<StaffResponse> responseList = staffList.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success("Staff retrieved successfully", responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving ordered staff by role: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper methods for conversion
    private OurStaffEntity convertToEntity(StaffCreateRequest request) {
        OurStaffEntity staff = new OurStaffEntity();
        staff.setStaffName(request.getStaffName());
        staff.setEmailAddress(request.getEmailAddress());
        staff.setPassword(request.getPassword());
        staff.setContactNumber(request.getContactNumber());
        staff.setJoiningDate(LocalDate.parse(request.getJoiningDate()));
        staff.setStaffRole(request.getStaffRole());
        return staff;
    }

    private OurStaffEntity convertToEntity(StaffUpdateRequest request) {
        OurStaffEntity entity = new OurStaffEntity();
        entity.setStaffName(request.getStaffName());
        entity.setEmailAddress(request.getEmailAddress());
        entity.setPassword(request.getPassword());
        entity.setContactNumber(request.getContactNumber());
        entity.setStaffRole(request.getStaffRole());

        // Handle date conversion
        if (request.getJoiningDate() != null && !request.getJoiningDate().trim().isEmpty()) {
            try {
                entity.setJoiningDate(LocalDate.parse(request.getJoiningDate()));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD");
            }
        }
        return entity;
    }

    private StaffResponse convertToResponse(OurStaffEntity entity) {
        return new StaffResponse(
                entity.getId(),
                entity.getStaffImage(),
                entity.getStaffName(),
                entity.getEmailAddress(),
                entity.getContactNumber(),
                entity.getJoiningDate(),
                entity.getStaffRole()
        );
    }
}