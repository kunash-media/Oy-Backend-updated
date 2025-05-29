package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.PrivacyPolicyEntity;
import com.oy.oy_jewels.service.PrivacyPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/privacy-policy")
public class PrivacyPolicyController {

    @Autowired
    private PrivacyPolicyService privacyPolicyService;

    @PostMapping("/create")
    public ResponseEntity<PrivacyPolicyEntity> createPrivacyPolicy(@RequestBody PrivacyPolicyEntity privacyPolicy) {
        PrivacyPolicyEntity createdPolicy = privacyPolicyService.createPrivacyPolicy(privacyPolicy);
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    @GetMapping("/get-All-Policy")
    public ResponseEntity<List<PrivacyPolicyEntity>> getAllPrivacyPolicies() {
        List<PrivacyPolicyEntity> policies = privacyPolicyService.getAllPrivacyPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivacyPolicyEntity> getPrivacyPolicyById(@PathVariable Long id) {
        Optional<PrivacyPolicyEntity> policy = privacyPolicyService.getPrivacyPolicyById(id);
        if (policy.isPresent()) {
            return new ResponseEntity<>(policy.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrivacyPolicyEntity> updatePrivacyPolicy(@PathVariable Long id, @RequestBody PrivacyPolicyEntity privacyPolicy) {
        PrivacyPolicyEntity updatedPolicy = privacyPolicyService.updatePrivacyPolicy(id, privacyPolicy);
        if (updatedPolicy != null) {
            return new ResponseEntity<>(updatedPolicy, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivacyPolicy(@PathVariable Long id) {
        privacyPolicyService.deletePrivacyPolicy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/latest")
    public ResponseEntity<PrivacyPolicyEntity> getLatestPrivacyPolicy() {
        PrivacyPolicyEntity policy = privacyPolicyService.getLatestPrivacyPolicy();
        if (policy != null) {
            return new ResponseEntity<>(policy, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}