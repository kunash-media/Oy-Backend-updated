package com.oy.oy_jewels.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oy.oy_jewels.dto.request.ProductCreateRequestDTO;
import com.oy.oy_jewels.dto.request.ProductDTO;
import com.oy.oy_jewels.dto.request.ProductDataDTO;
import com.oy.oy_jewels.dto.request.ProductPatchRequestDTO;
import com.oy.oy_jewels.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    // Create new product
    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("productData") String productData,
            @RequestPart("productImage") MultipartFile productImage,
            @RequestPart("productSubImages") MultipartFile[] productSubImages) {

        try {
            ProductDataDTO productDataDTO = objectMapper.readValue(productData, ProductDataDTO.class);

            ProductCreateRequestDTO requestDTO = new ProductCreateRequestDTO();
            requestDTO.setProductTitle(productDataDTO.getProductTitle());
            requestDTO.setProductPrice(new BigDecimal(productDataDTO.getProductPrice()));
            requestDTO.setProductOldPrice(new BigDecimal(productDataDTO.getProductOldPrice()));
            requestDTO.setProductImage(productImage.getBytes());
            requestDTO.setStoneColor(productDataDTO.getStoneColor());
            requestDTO.setMetalColor(productDataDTO.getMetalColor());
            requestDTO.setSkuNo(productDataDTO.getSkuNo());
            requestDTO.setRating(productDataDTO.getRating());

            List<byte[]> subImages = new ArrayList<>();
            for (MultipartFile file : productSubImages) {
                subImages.add(file.getBytes());
            }
            requestDTO.setProductSubImages(subImages);

            requestDTO.setProductDescription(productDataDTO.getProductDescription());
            requestDTO.setProductCategory(productDataDTO.getProductCategory());
            requestDTO.setProductStock(productDataDTO.getProductStock());
            requestDTO.setProductQuantity(productDataDTO.getProductQuantity());
            requestDTO.setShopBy(productDataDTO.getShopBy());
            requestDTO.setProductDiscount(productDataDTO.getProductDiscount());
            requestDTO.setProductCouponCode(productDataDTO.getProductCouponCode());

            requestDTO.setProductFeatures(productDataDTO.getProductFeatures());
            requestDTO.setProductSizes(productDataDTO.getProductSizes());
            requestDTO.setProductUnavailableSizes(productDataDTO.getProductUnavailableSizes());

            ProductDTO createdProduct = productService.createProduct(requestDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Failed to create product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all products
    @GetMapping("/get-all-product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        try {
            ProductDTO product = productService.getProductById(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //patch api
    @PatchMapping(value = "/update-product/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> patchProduct(
            @PathVariable Long productId,
            @RequestPart(value = "productData", required = false) String productData,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage,
            @RequestPart(value = "productSubImages", required = false) MultipartFile[] productSubImages) {

        try {
            ProductPatchRequestDTO patchRequest = new ProductPatchRequestDTO();

            // Parse JSON data if present
            if (productData != null) {
                ProductDataDTO productDataDTO = objectMapper.readValue(productData, ProductDataDTO.class);

                if (productDataDTO.getProductTitle() != null) patchRequest.setProductTitle(productDataDTO.getProductTitle());
                if (productDataDTO.getProductPrice() != null) patchRequest.setProductPrice(new BigDecimal(productDataDTO.getProductPrice()));
                if (productDataDTO.getProductOldPrice() != null) patchRequest.setProductOldPrice(new BigDecimal(productDataDTO.getProductOldPrice()));
                if (productDataDTO.getProductDescription() != null) patchRequest.setProductDescription(productDataDTO.getProductDescription());
                if (productDataDTO.getProductCategory() != null) patchRequest.setProductCategory(productDataDTO.getProductCategory());
                if (productDataDTO.getProductStock() != null) patchRequest.setProductStock(productDataDTO.getProductStock());
                if (productDataDTO.getProductQuantity() != null) patchRequest.setProductQuantity(productDataDTO.getProductQuantity());
                if (productDataDTO.getShopBy() != null) patchRequest.setShopBy(productDataDTO.getShopBy());
                if (productDataDTO.getProductDiscount() != null) patchRequest.setProductDiscount(productDataDTO.getProductDiscount());
                if (productDataDTO.getProductCouponCode() != null) patchRequest.setProductCouponCode(productDataDTO.getProductCouponCode());
                if (productDataDTO.getStoneColor() != null) patchRequest.setStoneColor(productDataDTO.getStoneColor());
                if (productDataDTO.getMetalColor() != null) patchRequest.setMetalColor(productDataDTO.getMetalColor());
                if (productDataDTO.getSkuNo() != null) patchRequest.setSkuNo(productDataDTO.getSkuNo());
                if (productDataDTO.getRating() != null) patchRequest.setRating(productDataDTO.getRating());

                if (productDataDTO.getProductFeatures() != null) patchRequest.setProductFeatures(productDataDTO.getProductFeatures());
                if (productDataDTO.getProductSizes() != null) patchRequest.setProductSizes(productDataDTO.getProductSizes());
                if (productDataDTO.getProductUnavailableSizes() != null) patchRequest.setProductUnavailableSizes(productDataDTO.getProductUnavailableSizes());
            }

            if (productImage != null && !productImage.isEmpty()) {
                patchRequest.setProductImage(productImage.getBytes());
                patchRequest.setProductImagePresent(true);
            }

            if (productSubImages != null && productSubImages.length > 0) {
                List<byte[]> subImages = new ArrayList<>();
                for (MultipartFile file : productSubImages) {
                    subImages.add(file.getBytes());
                }
                patchRequest.setProductSubImages(subImages);
                patchRequest.setProductSubImagesPresent(true);
            }

            ProductDTO updatedProduct = productService.patchProduct(productId, patchRequest);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update product
    @PutMapping(value = "/update-product/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long productId,
            @RequestPart("productData") String productData,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage,
            @RequestPart(value = "productSubImages", required = false) MultipartFile[] productSubImages) {

        try {
            // Parse the JSON productData
            ProductDataDTO productDataDTO = objectMapper.readValue(productData, ProductDataDTO.class);

            ProductCreateRequestDTO requestDTO = new ProductCreateRequestDTO();
            requestDTO.setProductTitle(productDataDTO.getProductTitle());
            requestDTO.setProductPrice(new BigDecimal(productDataDTO.getProductPrice()));
            requestDTO.setProductOldPrice(new BigDecimal(productDataDTO.getProductOldPrice()));

            if (productImage != null && !productImage.isEmpty()) {
                requestDTO.setProductImage(productImage.getBytes());
            }

            if (productSubImages != null && productSubImages.length > 0) {
                List<byte[]> subImages = new ArrayList<>();
                for (MultipartFile file : productSubImages) {
                    subImages.add(file.getBytes());
                }
                requestDTO.setProductSubImages(subImages);
            }

            requestDTO.setProductDescription(productDataDTO.getProductDescription());
            requestDTO.setProductCategory(productDataDTO.getProductCategory());
            requestDTO.setProductStock(productDataDTO.getProductStock());
            requestDTO.setProductQuantity(productDataDTO.getProductQuantity());
            requestDTO.setShopBy(productDataDTO.getShopBy());
            requestDTO.setProductDiscount(productDataDTO.getProductDiscount());
            requestDTO.setProductCouponCode(productDataDTO.getProductCouponCode());
            requestDTO.setStoneColor(productDataDTO.getStoneColor());
            requestDTO.setMetalColor(productDataDTO.getMetalColor());
            requestDTO.setSkuNo(productDataDTO.getSkuNo());
            requestDTO.setRating(productDataDTO.getRating());

            requestDTO.setProductFeatures(productDataDTO.getProductFeatures());
            requestDTO.setProductSizes(productDataDTO.getProductSizes());
            requestDTO.setProductUnavailableSizes(productDataDTO.getProductUnavailableSizes());

            ProductDTO updatedProduct = productService.updateProduct(productId, requestDTO);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete product
    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid product ID", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search products by title
    @GetMapping("/search/{title}")
    public ResponseEntity<List<ProductDTO>> getProductsByTitle(@PathVariable String title) {
        try {
            List<ProductDTO> products = productService.getProductsByTitle(title);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if product exists
    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean> checkProductExists(@PathVariable Long productId) {
        try {
            boolean exists = productService.existsById(productId);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get product count
    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        try {
            long count = productService.getProductCount();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        try {
            List<ProductDTO> products = productService.getProductsByCategory(category);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get products by shopBy
    @GetMapping("/shop-by/{shopBy}")
    public ResponseEntity<List<ProductDTO>> getProductsByShopBy(@PathVariable String shopBy) {
        try {
            List<ProductDTO> products = productService.getProductsByShopBy(shopBy);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}