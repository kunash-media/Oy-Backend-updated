package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create new product
    @PostMapping("/create-product")
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        ProductEntity createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Get all products
    @GetMapping("get-all-product")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long productId) {
        ProductEntity product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Update product
    @PutMapping("/update-product/{productId}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long productId, @RequestBody ProductEntity product) {
        ProductEntity updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete product
    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductEntity>> getProductsByCategory(@PathVariable String category) {
        List<ProductEntity> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get products by stock status
    @GetMapping("/stock/{stock}")
    public ResponseEntity<List<ProductEntity>> getProductsByStock(@PathVariable String stock) {
        List<ProductEntity> products = productService.getProductsByStock(stock);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



}
