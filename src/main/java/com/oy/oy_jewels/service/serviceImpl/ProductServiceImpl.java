package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.repository.ProductRepository;
import com.oy.oy_jewels.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        // Set default stock status if not provided
        if (product.getStock() == null || product.getStock().isEmpty()) {
            product.setStock("instock");
        }
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    @Override
    public ProductEntity updateProduct(Long productId, ProductEntity product) {
        ProductEntity existingProduct = getProductById(productId);

        // Update fields
        existingProduct.setProductName(product.getProductName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setCouponCode(product.getCouponCode());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setOffer(product.getOffer());
        existingProduct.setGst(product.getGst());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setShopBy(product.getShopBy());
        existingProduct.setTotalPrice(product.getTotalPrice());

        // Update image only if new image is provided
        if (product.getProductImage() != null) {
            existingProduct.setProductImage(product.getProductImage());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        ProductEntity product = getProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public List<ProductEntity> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<ProductEntity> getProductsByStock(String stock) {
        return productRepository.findByStock(stock);
    }
}