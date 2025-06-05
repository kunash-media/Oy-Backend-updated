package com.oy.oy_jewels.controller;


import com.oy.oy_jewels.dto.request.ShippingRequestDTO;
import com.oy.oy_jewels.dto.response.ShippingResponseDTO;
import com.oy.oy_jewels.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/create-shipping")
    public ResponseEntity<ShippingResponseDTO> createShipping(@RequestBody ShippingRequestDTO shippingRequest) {
        ShippingResponseDTO createdShipping = shippingService.createShipping(shippingRequest);
        return new ResponseEntity<>(createdShipping, HttpStatus.CREATED);
    }

    @GetMapping("/get-All-Shippings")
    public ResponseEntity<List<ShippingResponseDTO>> getAllShippings() {
        List<ShippingResponseDTO> shippings = shippingService.getAllShippings();
        return new ResponseEntity<>(shippings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingResponseDTO> getShippingById(@PathVariable Long id) {
        ShippingResponseDTO shipping = shippingService.getShippingById(id);
        if (shipping != null) {
            return new ResponseEntity<>(shipping, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingResponseDTO> updateShipping(@PathVariable Long id, @RequestBody ShippingRequestDTO shippingRequest) {
        ShippingResponseDTO updatedShipping = shippingService.updateShipping(id, shippingRequest);
        if (updatedShipping != null) {
            return new ResponseEntity<>(updatedShipping, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(@PathVariable Long id) {
        shippingService.deleteShipping(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<ShippingResponseDTO>> searchByTitle(@RequestParam String title) {
        List<ShippingResponseDTO> shippings = shippingService.searchByTitle(title);
        return new ResponseEntity<>(shippings, HttpStatus.OK);
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<ShippingResponseDTO>> searchByDescription(@RequestParam String description) {
        List<ShippingResponseDTO> shippings = shippingService.searchByDescription(description);
        return new ResponseEntity<>(shippings, HttpStatus.OK);
    }
}