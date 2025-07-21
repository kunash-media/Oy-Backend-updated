package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.dto.request.ShippingAddressDTO;
import com.oy.oy_jewels.service.ShippingAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class ShippingAddressController {

    private final ShippingAddressService service;

    public ShippingAddressController(ShippingAddressService service) {
        this.service = service;
    }

    @PostMapping("/create-address/{userId}")
    public ResponseEntity<ShippingAddressDTO> createAddress(
            @PathVariable Long userId,
            @RequestBody ShippingAddressDTO addressDTO) {
        return ResponseEntity.ok(service.createAddress(userId, addressDTO));
    }

    @GetMapping("/get-address-by-userId/{userId}")
    public ResponseEntity<List<ShippingAddressDTO>> getAddressesByUserId(
            @PathVariable Long userId) {
        return ResponseEntity.ok(service.getAddressesByUserId(userId));
    }

    @GetMapping("/get-all-address")
    public ResponseEntity<List<ShippingAddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(service.getAllAddresses());
    }

    @PatchMapping("/patch-address/{userId}/{shippingId}")
    public ResponseEntity<ShippingAddressDTO> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long shippingId,
            @RequestBody ShippingAddressDTO addressDTO) {
        return ResponseEntity.ok(service.updateAddress(userId, shippingId, addressDTO));
    }

    @DeleteMapping("/delete-address/{userId}/{shippingId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long shippingId) {
        service.deleteAddress(userId, shippingId);
        return ResponseEntity.ok().build();
    }
}