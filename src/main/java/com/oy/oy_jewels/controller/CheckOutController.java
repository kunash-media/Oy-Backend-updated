package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.CheckOutEntity;
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

    @PostMapping("/create-CheckOut")
    public ResponseEntity<CheckOutEntity> createCheckOut(@RequestBody CheckOutEntity checkOut) {
        CheckOutEntity createdCheckOut = checkOutService.createCheckOut(checkOut);
        return new ResponseEntity<>(createdCheckOut, HttpStatus.CREATED);
    }

    @GetMapping("/get-AllCheckOuts")
    public ResponseEntity<List<CheckOutEntity>> getAllCheckOuts() {
        List<CheckOutEntity> checkOuts = checkOutService.getAllCheckOuts();
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckOutEntity> getCheckOutById(@PathVariable Long id) {
        Optional<CheckOutEntity> checkOut = checkOutService.getCheckOutById(id);
        if (checkOut.isPresent()) {
            return new ResponseEntity<>(checkOut.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CheckOutEntity> updateCheckOut(@PathVariable Long id, @RequestBody CheckOutEntity checkOut) {
        CheckOutEntity updatedCheckOut = checkOutService.updateCheckOut(id, checkOut);
        if (updatedCheckOut != null) {
            return new ResponseEntity<>(updatedCheckOut, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckOut(@PathVariable Long id) {
        checkOutService.deleteCheckOut(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<CheckOutEntity>> getCheckOutsByEmail(@PathVariable String email) {
        List<CheckOutEntity> checkOuts = checkOutService.getCheckOutsByEmail(email);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<List<CheckOutEntity>> getCheckOutsByPhoneNumber(@PathVariable String phoneNumber) {
        List<CheckOutEntity> checkOuts = checkOutService.getCheckOutsByPhoneNumber(phoneNumber);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<CheckOutEntity>> getCheckOutsByCity(@PathVariable String city) {
        List<CheckOutEntity> checkOuts = checkOutService.getCheckOutsByCity(city);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<CheckOutEntity>> getCheckOutsByCountry(@PathVariable String country) {
        List<CheckOutEntity> checkOuts = checkOutService.getCheckOutsByCountry(country);
        return new ResponseEntity<>(checkOuts, HttpStatus.OK);
    }
}

