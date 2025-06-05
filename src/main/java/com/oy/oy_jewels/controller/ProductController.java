package com.oy.oy_jewels.controller;

import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create new product with image
    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductEntity> createProduct(
            @RequestPart("productName") String productName,
            @RequestPart("category") String category,
            @RequestPart(value = "couponCode", required = false) String couponCode,
            @RequestPart("quantity") String quantity,
            @RequestPart(value = "offer", required = false) String offer,
            @RequestPart("gst") String gst,
            @RequestPart("discount") String discount,
            @RequestPart("productPrice") String productPrice,
            @RequestPart(value = "stock", required = false) String stock,
            @RequestPart("shopBy") String shopBy,
            @RequestPart("totalPrice") String totalPrice,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage) throws IOException {

        ProductEntity product = new ProductEntity();
        product.setProductName(productName);
        product.setCategory(category);
        product.setCouponCode(couponCode);
        product.setQuantity(Integer.parseInt(quantity));
        product.setOffer(offer);
        product.setGst(new BigDecimal(gst));
        product.setDiscount(new BigDecimal(discount));
        product.setProductPrice(new BigDecimal(productPrice));
        product.setStock(stock);
        product.setShopBy(shopBy);
        product.setTotalPrice(new BigDecimal(totalPrice));

        if (productImage != null && !productImage.isEmpty()) {
            product.setProductImage(productImage.getBytes());
        }

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

    // Get product image by ID
    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
        ProductEntity product = productService.getProductById(productId);
        if (product.getProductImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getProductImage());
        }
        return ResponseEntity.notFound().build();
    }

    // Update product with image
    @PutMapping(value = "/update-product/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long productId,
            @RequestPart("productName") String productName,
            @RequestPart("category") String category,
            @RequestPart(value = "couponCode", required = false) String couponCode,
            @RequestPart("quantity") String quantity,
            @RequestPart(value = "offer", required = false) String offer,
            @RequestPart("gst") String gst,
            @RequestPart("discount") String discount,
            @RequestPart("productPrice") String productPrice,
            @RequestPart(value = "stock", required = false) String stock,
            @RequestPart("shopBy") String shopBy,
            @RequestPart("totalPrice") String totalPrice,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage) throws IOException {

        ProductEntity product = new ProductEntity();
        product.setProductName(productName);
        product.setCategory(category);
        product.setCouponCode(couponCode);
        product.setQuantity(Integer.parseInt(quantity));
        product.setOffer(offer);
        product.setGst(new BigDecimal(gst));
        product.setDiscount(new BigDecimal(discount));
        product.setProductPrice(new BigDecimal(productPrice));
        product.setStock(stock);
        product.setShopBy(shopBy);
        product.setTotalPrice(new BigDecimal(totalPrice));

        if (productImage != null && !productImage.isEmpty()) {
            product.setProductImage(productImage.getBytes());
        }

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