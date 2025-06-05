package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.PrivacyPolicyRequestDTO;
import com.oy.oy_jewels.dto.response.PrivacyPolicyResponseDTO;
import com.oy.oy_jewels.entity.PrivacyPolicyEntity;
import com.oy.oy_jewels.mapper.PrivacyPolicyMapper;
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

    @Autowired
    private PrivacyPolicyMapper privacyPolicyMapper;

    @PostMapping("/create")
    public ResponseEntity<PrivacyPolicyResponseDTO> createPrivacyPolicy( @RequestBody PrivacyPolicyRequestDTO requestDTO) {
        PrivacyPolicyEntity entity = privacyPolicyMapper.toEntity(requestDTO);
        PrivacyPolicyEntity createdEntity = privacyPolicyService.createPrivacyPolicy(entity);
        PrivacyPolicyResponseDTO responseDTO = privacyPolicyMapper.toResponseDTO(createdEntity);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get-All-Policy")
    public ResponseEntity<List<PrivacyPolicyResponseDTO>> getAllPrivacyPolicies() {
        List<PrivacyPolicyEntity> entities = privacyPolicyService.getAllPrivacyPolicies();
        List<PrivacyPolicyResponseDTO> responseDTOs = privacyPolicyMapper.toResponseDTOList(entities);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivacyPolicyResponseDTO> getPrivacyPolicyById(@PathVariable Long id) {
        Optional<PrivacyPolicyEntity> entity = privacyPolicyService.getPrivacyPolicyById(id);
        if (entity.isPresent()) {
            PrivacyPolicyResponseDTO responseDTO = privacyPolicyMapper.toResponseDTO(entity.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrivacyPolicyResponseDTO> updatePrivacyPolicy(@PathVariable Long id, @RequestBody PrivacyPolicyRequestDTO requestDTO) {
        PrivacyPolicyEntity entity = privacyPolicyMapper.toEntity(requestDTO);
        PrivacyPolicyEntity updatedEntity = privacyPolicyService.updatePrivacyPolicy(id, entity);
        if (updatedEntity != null) {
            PrivacyPolicyResponseDTO responseDTO = privacyPolicyMapper.toResponseDTO(updatedEntity);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivacyPolicy(@PathVariable Long id) {
        privacyPolicyService.deletePrivacyPolicy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/latest")
    public ResponseEntity<PrivacyPolicyResponseDTO> getLatestPrivacyPolicy() {
        PrivacyPolicyEntity entity = privacyPolicyService.getLatestPrivacyPolicy();
        if (entity != null) {
            PrivacyPolicyResponseDTO responseDTO = privacyPolicyMapper.toResponseDTO(entity);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}