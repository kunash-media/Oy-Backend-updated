package com.oy.oy_jewels.service;

import com.oy.oy_jewels.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    // Create new product
    ProductEntity createProduct(ProductEntity product);

    // Get all products
    List<ProductEntity> getAllProducts();

    // Get product by ID
    ProductEntity getProductById(Long productId);

    // Update product
    ProductEntity updateProduct(Long productId, ProductEntity product);

    // Delete product
    void deleteProduct(Long productId);

    // Get products by category
    List<ProductEntity> getProductsByCategory(String category);

    // Get products by stock status
    List<ProductEntity> getProductsByStock(String stock);
}