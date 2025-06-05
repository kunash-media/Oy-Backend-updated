package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.StaffCreateRequest;
import com.oy.oy_jewels.dto.response.ApiResponse;
import com.oy.oy_jewels.dto.response.StaffResponse;
import com.oy.oy_jewels.entity.OurStaffEntity;
import com.oy.oy_jewels.service.OurStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class OurStaffController {

    @Autowired
    private OurStaffService staffService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(
            @RequestPart(value = "staffImage", required = false) MultipartFile staffImage,
            @RequestPart("staffName") String staffName,
            @RequestPart("emailAddress") String emailAddress,
            @RequestPart("password") String password,
            @RequestPart("contactNumber") String contactNumber,
            @RequestPart("joiningDate") String joiningDate,
            @RequestPart("staffRole") String staffRole) {

        try {
            StaffCreateRequest request = new StaffCreateRequest(staffName, emailAddress, password,
                    contactNumber, joiningDate, staffRole);

            OurStaffEntity staff = convertToEntity(request);
            OurStaffEntity createdStaff = staffService.createStaff(staff, staffImage);
            StaffResponse response = convertToResponse(createdStaff);

            return new ResponseEntity<>(ApiResponse.success("Staff created successfully", response), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error creating staff: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<StaffResponse>>> getAllStaff() {
        try {
            List<OurStaffEntity> staffList = staffService.getAllStaff();
            List<StaffResponse> responseList = staffList.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(ApiResponse.success(responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffById(@PathVariable Long id) {
        try {
            Optional<OurStaffEntity> staff = staffService.getStaffById(id);
            if (staff.isPresent()) {
                StaffResponse response = convertToResponse(staff.get());
                return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("Staff not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error retrieving staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{emailAddress}")
    public ResponseEntity<ApiResponse<StaffResponse>> getStaffByEmail(@PathVariable String emailAddress) {
        try {
            Optional<OurStaffEntity> staff = staffService.getStaffByEmail(emailAddress);
            if (staff.isPresent()) {
                StaffResponse response = convertToResponse(staff.get());
                return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
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

            return new ResponseEntity<>(ApiResponse.success(responseList), HttpStatus.OK);
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

            return new ResponseEntity<>(ApiResponse.success(responseList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error searching staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<StaffResponse>> updateStaff(
            @PathVariable Long id,
            @RequestParam(value = "staffImage", required = false) MultipartFile staffImage,
            @RequestParam("staffName") String staffName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("password") String password,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("joiningDate") String joiningDate,
            @RequestParam("staffRole") String staffRole) {

        try {
            StaffUpdateRequest request = new StaffUpdateRequest(staffName, emailAddress, password,
                    contactNumber, joiningDate, staffRole);

            OurStaffEntity staff = convertToEntity(request);
            OurStaffEntity updatedStaff = staffService.updateStaff(id, staff, staffImage);

            if (updatedStaff != null) {
                StaffResponse response = convertToResponse(updatedStaff);
                return new ResponseEntity<>(ApiResponse.success("Staff updated successfully", response), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("Staff not found"), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error updating staff: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStaff(@PathVariable Long id) {
        try {
            boolean deleted = staffService.deleteStaff(id);
            if (deleted) {
                return new ResponseEntity<>(ApiResponse.success("Staff deleted successfully", "Deleted"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ApiResponse.error("Staff not found"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Error deleting staff: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/check-email/{emailAddress}")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(@PathVariable String emailAddress) {
        try {
            boolean exists = staffService.emailExists(emailAddress);
            return new ResponseEntity<>(ApiResponse.success(exists), HttpStatus.OK);
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

            return new ResponseEntity<>(ApiResponse.success(responseList), HttpStatus.OK);
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

            return new ResponseEntity<>(ApiResponse.success(responseList), HttpStatus.OK);
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
        OurStaffEntity staff = new OurStaffEntity();
        staff.setStaffName(request.getStaffName());
        staff.setEmailAddress(request.getEmailAddress());
        staff.setPassword(request.getPassword());
        staff.setContactNumber(request.getContactNumber());
        staff.setJoiningDate(LocalDate.parse(request.getJoiningDate()));
        staff.setStaffRole(request.getStaffRole());
        return staff;
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