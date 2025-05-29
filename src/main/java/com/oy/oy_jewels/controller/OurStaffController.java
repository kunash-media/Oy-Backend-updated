package com.oy.oy_jewels.controller;

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

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class OurStaffController {

    @Autowired
    private OurStaffService staffService;

    @PostMapping("/create")
    public ResponseEntity<?> createStaff(
            @RequestPart("staffImage") MultipartFile staffImage,
            @RequestPart("staffName") String staffName,
            @RequestPart("emailAddress") String emailAddress,
            @RequestPart("password") String password,
            @RequestPart("contactNumber") String contactNumber,
            @RequestPart("joiningDate") String joiningDate,
            @RequestPart("staffRole") String staffRole) {

        try {
            OurStaffEntity staff = new OurStaffEntity();
            staff.setStaffName(staffName);
            staff.setEmailAddress(emailAddress);
            staff.setPassword(password);
            staff.setContactNumber(contactNumber);
            staff.setJoiningDate(LocalDate.parse(joiningDate));
            staff.setStaffRole(staffRole);

            OurStaffEntity createdStaff = staffService.createStaff(staff, staffImage);
            return new ResponseEntity<>(createdStaff, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error creating staff: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // Get all staff
    @GetMapping("/all")
    public ResponseEntity<List<OurStaffEntity>> getAllStaff() {
        List<OurStaffEntity> staffList = staffService.getAllStaff();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    // Get staff by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        Optional<OurStaffEntity> staff = staffService.getStaffById(id);
        if (staff.isPresent()) {
            return new ResponseEntity<>(staff.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Staff not found", HttpStatus.NOT_FOUND);
    }

    // Get staff by email
    @GetMapping("/email/{emailAddress}")
    public ResponseEntity<?> getStaffByEmail(@PathVariable String emailAddress) {
        Optional<OurStaffEntity> staff = staffService.getStaffByEmail(emailAddress);
        if (staff.isPresent()) {
            return new ResponseEntity<>(staff.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Staff not found", HttpStatus.NOT_FOUND);
    }

    // Get staff by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<OurStaffEntity>> getStaffByRole(@PathVariable String role) {
        List<OurStaffEntity> staffList = staffService.getStaffByRole(role);
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    // Search staff by name
    @GetMapping("/search")
    public ResponseEntity<List<OurStaffEntity>> searchStaffByName(@RequestParam String name) {
        List<OurStaffEntity> staffList = staffService.searchStaffByName(name);
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    // Update staff
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStaff(
            @PathVariable Long id,
            @RequestParam(value = "staffImage", required = false) MultipartFile staffImage,
            @RequestParam("staffName") String staffName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("password") String password,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("joiningDate") String joiningDate,
            @RequestParam("staffRole") String staffRole) {

        try {
            OurStaffEntity staff = new OurStaffEntity();
            staff.setStaffName(staffName);
            staff.setEmailAddress(emailAddress);
            staff.setPassword(password);
            staff.setContactNumber(contactNumber);
            staff.setJoiningDate(LocalDate.parse(joiningDate));
            staff.setStaffRole(staffRole);

            OurStaffEntity updatedStaff = staffService.updateStaff(id, staff, staffImage);

            if (updatedStaff != null) {
                return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
            }
            return new ResponseEntity<>("Staff not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Error updating staff: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete staff
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        boolean deleted = staffService.deleteStaff(id);
        if (deleted) {
            return new ResponseEntity<>("Staff deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Staff not found", HttpStatus.NOT_FOUND);
    }

    // Check if email exists
    @GetMapping("/check-email/{emailAddress}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String emailAddress) {
        boolean exists = staffService.emailExists(emailAddress);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    // Get staff ordered by joining date
    @GetMapping("/ordered-by-joining-date")
    public ResponseEntity<List<OurStaffEntity>> getStaffOrderedByJoiningDate() {
        List<OurStaffEntity> staffList = staffService.getStaffOrderedByJoiningDate();
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    // Get staff by role ordered by joining date
    @GetMapping("/role/{role}/ordered-by-joining-date")
    public ResponseEntity<List<OurStaffEntity>> getStaffByRoleOrderedByJoiningDate(@PathVariable String role) {
        List<OurStaffEntity> staffList = staffService.getStaffByRoleOrderedByJoiningDate(role);
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }


}
