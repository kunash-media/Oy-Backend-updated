package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.CheckOutRequestDTO;
import com.oy.oy_jewels.dto.response.CheckOutResponseDTO;
import com.oy.oy_jewels.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkout")
public class CheckOutController {

    @Autowired
    private CheckOutService checkOutService;

    @PostMapping
    public ResponseEntity<CheckOutResponseDTO> createCheckOut(@RequestBody CheckOutRequestDTO checkOutRequestDTO) {
        CheckOutResponseDTO response = checkOutService.createCheckOut(checkOutRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CheckOutResponseDTO>> getAllCheckOuts() {
        List<CheckOutResponseDTO> checkOuts = checkOutService.getAllCheckOuts();
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckOutResponseDTO> getCheckOutById(@PathVariable Long id) {
        Optional<CheckOutResponseDTO> checkOut = checkOutService.getCheckOutById(id);
        return checkOut.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckOutResponseDTO> updateCheckOut(@PathVariable Long id,
                                                              @RequestBody CheckOutRequestDTO checkOutRequestDTO) {
        CheckOutResponseDTO response = checkOutService.updateCheckOut(id, checkOutRequestDTO);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckOut(@PathVariable Long id) {
        checkOutService.deleteCheckOut(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<CheckOutResponseDTO>> getCheckOutsByEmail(@PathVariable String email) {
        List<CheckOutResponseDTO> checkOuts = checkOutService.getCheckOutsByEmail(email);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<List<CheckOutResponseDTO>> getCheckOutsByPhoneNumber(@PathVariable String phoneNumber) {
        List<CheckOutResponseDTO> checkOuts = checkOutService.getCheckOutsByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<CheckOutResponseDTO>> getCheckOutsByCity(@PathVariable String city) {
        List<CheckOutResponseDTO> checkOuts = checkOutService.getCheckOutsByCity(city);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<CheckOutResponseDTO>> getCheckOutsByCountry(@PathVariable String country) {
        List<CheckOutResponseDTO> checkOuts = checkOutService.getCheckOutsByCountry(country);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }
}