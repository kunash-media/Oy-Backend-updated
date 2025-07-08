package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.UserEntity;
import com.oy.oy_jewels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserEntity> authenticateUser(
            @RequestParam("mobile") String mobile,
            @RequestParam("password") String password) {

        Optional<UserEntity> user = userService.authenticateUserByMobileAndPassword(mobile, password);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long userId, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // NEW: PATCH endpoint for partial updates
    @PatchMapping("/update/{userId}")
    public ResponseEntity<UserEntity> patchUser(@PathVariable Long userId, @RequestBody Map<String, Object> updates) {
        UserEntity patchedUser = userService.patchUser(userId, updates);
        return new ResponseEntity<>(patchedUser, HttpStatus.OK);
    }

    // password changing api
    @PatchMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {

        try {
            boolean success = userService.changePassword(userId, oldPassword, newPassword);
            return ResponseEntity.ok().body("Password changed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserEntity>> getUsersByStatus(@PathVariable String status) {
        List<UserEntity> users = userService.getUsersByStatus(status);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        UserEntity user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
