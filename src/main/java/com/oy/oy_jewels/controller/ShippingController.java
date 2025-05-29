package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.ShippingEntity;
import com.oy.oy_jewels.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/create-shipping")
    public ResponseEntity<ShippingEntity> createShipping(@RequestBody ShippingEntity shipping) {
        ShippingEntity createdShipping = shippingService.createShipping(shipping);
        return new ResponseEntity<>(createdShipping, HttpStatus.CREATED);
    }

    @GetMapping("/get-All-Shippings")
    public ResponseEntity<List<ShippingEntity>> getAllShippings() {
        List<ShippingEntity> shippings = shippingService.getAllShippings();
        return new ResponseEntity<>(shippings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingEntity> getShippingById(@PathVariable Long id) {
        Optional<ShippingEntity> shipping = shippingService.getShippingById(id);
        if (shipping.isPresent()) {
            return new ResponseEntity<>(shipping.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingEntity> updateShipping(@PathVariable Long id, @RequestBody ShippingEntity shipping) {
        ShippingEntity updatedShipping = shippingService.updateShipping(id, shipping);
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
    public ResponseEntity<List<ShippingEntity>> searchByTitle(@RequestParam String title) {
        List<ShippingEntity> shippings = shippingService.searchByTitle(title);
        return new ResponseEntity<>(shippings, HttpStatus.OK);
    }

    @GetMapping("/search/description")
    public ResponseEntity<List<ShippingEntity>> searchByDescription(@RequestParam String description) {
        List<ShippingEntity> shippings = shippingService.searchByDescription(description);
        return new ResponseEntity<>(shippings, HttpStatus.OK);
    }
}
