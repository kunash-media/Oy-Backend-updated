package com.oy.oy_jewels.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oy.oy_jewels.dto.request.ProductCreateRequestDTO;
import com.oy.oy_jewels.dto.request.ProductDTO;
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

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    // Create new product
    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(
            @RequestPart("productTitle") String productTitle,
            @RequestPart("productPrice") String productPrice,
            @RequestPart("productOldPrice") String productOldPrice,
            @RequestPart("productImage") MultipartFile productImage,
            @RequestPart("productSubImages") MultipartFile[] productSubImages,
            @RequestPart("productDescription") String productDescription,
            @RequestPart("productFeatures") String productFeatures,
            @RequestPart("productSizes") String productSizes,
            @RequestPart("productUnavailableSizes") String productUnavailableSizes,
            @RequestPart("productCategory") String productCategory,
            @RequestPart("productStock") String productStock,
            @RequestPart("productQuantity") String productQuantity,
            @RequestPart("shopBy") String shopBy,
            @RequestPart("productDiscount") String productDiscount,
            @RequestPart("productCouponCode") String productCouponCode) {

        try {
            ProductCreateRequestDTO requestDTO = new ProductCreateRequestDTO();
            requestDTO.setProductTitle(productTitle);
            requestDTO.setProductPrice(new BigDecimal(productPrice));
            requestDTO.setProductOldPrice(new BigDecimal(productOldPrice));
            requestDTO.setProductImage(productImage.getBytes());

            List<byte[]> subImages = new ArrayList<>();
            for (MultipartFile file : productSubImages) {
                subImages.add(file.getBytes());
            }
            requestDTO.setProductSubImages(subImages);

            requestDTO.setProductDescription(productDescription);
            requestDTO.setProductCategory(productCategory);
            requestDTO.setProductStock(productStock);
            requestDTO.setProductQuantity(Integer.parseInt(productQuantity));
            requestDTO.setShopBy(shopBy);
            requestDTO.setProductDiscount(productDiscount);
            requestDTO.setProductCouponCode(productCouponCode);

            // Parse JSON arrays
            List<String> features = objectMapper.readValue(productFeatures, new TypeReference<List<String>>() {});
            List<String> sizes = objectMapper.readValue(productSizes, new TypeReference<List<String>>() {});
            List<String> unavailableSizes = objectMapper.readValue(productUnavailableSizes, new TypeReference<List<String>>() {});

            requestDTO.setProductFeatures(features);
            requestDTO.setProductSizes(sizes);
            requestDTO.setProductUnavailableSizes(unavailableSizes);

            ProductDTO createdProduct = productService.createProduct(requestDTO);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
            @RequestPart(value = "productTitle", required = false) String productTitle,
            @RequestPart(value = "productPrice", required = false) String productPrice,
            @RequestPart(value = "productOldPrice", required = false) String productOldPrice,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage,
            @RequestPart(value = "productSubImages", required = false) MultipartFile[] productSubImages,
            @RequestPart(value = "productDescription", required = false) String productDescription,
            @RequestPart(value = "productFeatures", required = false) String productFeatures,
            @RequestPart(value = "productSizes", required = false) String productSizes,
            @RequestPart(value = "productUnavailableSizes", required = false) String productUnavailableSizes,
            @RequestPart(value = "productCategory", required = false) String productCategory,
            @RequestPart(value = "productStock", required = false) String productStock,
            @RequestPart(value = "productQuantity", required = false) String productQuantity,
            @RequestPart(value = "shopBy", required = false) String shopBy,
            @RequestPart(value = "productDiscount", required = false) String productDiscount,
            @RequestPart(value = "productCouponCode", required = false) String productCouponCode) {

        try {
            ProductPatchRequestDTO patchRequest = new ProductPatchRequestDTO();

            if (productTitle != null) patchRequest.setProductTitle(productTitle);
            if (productPrice != null) patchRequest.setProductPrice(new BigDecimal(productPrice));
            if (productOldPrice != null) patchRequest.setProductOldPrice(new BigDecimal(productOldPrice));

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

            if (productDescription != null) patchRequest.setProductDescription(productDescription);
            if (productCategory != null) patchRequest.setProductCategory(productCategory);
            if (productStock != null) patchRequest.setProductStock(productStock);
            if (productQuantity != null) patchRequest.setProductQuantity(Integer.parseInt(productQuantity));
            if (shopBy != null) patchRequest.setShopBy(shopBy);
            if (productDiscount != null) patchRequest.setProductDiscount(productDiscount);
            if (productCouponCode != null) patchRequest.setProductCouponCode(productCouponCode);

            // Parse JSON arrays if present
            if (productFeatures != null) {
                List<String> features = objectMapper.readValue(productFeatures, new TypeReference<List<String>>() {});
                patchRequest.setProductFeatures(features);
            }
            if (productSizes != null) {
                List<String> sizes = objectMapper.readValue(productSizes, new TypeReference<List<String>>() {});
                patchRequest.setProductSizes(sizes);
            }
            if (productUnavailableSizes != null) {
                List<String> unavailableSizes = objectMapper.readValue(productUnavailableSizes, new TypeReference<List<String>>() {});
                patchRequest.setProductUnavailableSizes(unavailableSizes);
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
            @RequestPart("productTitle") String productTitle,
            @RequestPart("productPrice") String productPrice,
            @RequestPart("productOldPrice") String productOldPrice,
            @RequestPart(value = "productImage", required = false) MultipartFile productImage,
            @RequestPart(value = "productSubImages", required = false) MultipartFile[] productSubImages,
            @RequestPart("productDescription") String productDescription,
            @RequestPart("productFeatures") String productFeatures,
            @RequestPart("productSizes") String productSizes,
            @RequestPart("productUnavailableSizes") String productUnavailableSizes,
            @RequestPart("productCategory") String productCategory,
            @RequestPart("productStock") String productStock,
            @RequestPart("productQuantity") String productQuantity,
            @RequestPart("shopBy") String shopBy,
            @RequestPart("productDiscount") String productDiscount,
            @RequestPart("productCouponCode") String productCouponCode) {

        try {
            ProductCreateRequestDTO requestDTO = new ProductCreateRequestDTO();
            requestDTO.setProductTitle(productTitle);
            requestDTO.setProductPrice(new BigDecimal(productPrice));
            requestDTO.setProductOldPrice(new BigDecimal(productOldPrice));

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

            requestDTO.setProductDescription(productDescription);
            requestDTO.setProductCategory(productCategory);
            requestDTO.setProductStock(productStock);
            requestDTO.setProductQuantity(Integer.parseInt(productQuantity));
            requestDTO.setShopBy(shopBy);
            requestDTO.setProductDiscount(productDiscount);
            requestDTO.setProductCouponCode(productCouponCode);

            // Parse JSON arrays
            List<String> features = objectMapper.readValue(productFeatures, new TypeReference<List<String>>() {});
            List<String> sizes = objectMapper.readValue(productSizes, new TypeReference<List<String>>() {});
            List<String> unavailableSizes = objectMapper.readValue(productUnavailableSizes, new TypeReference<List<String>>() {});

            requestDTO.setProductFeatures(features);
            requestDTO.setProductSizes(sizes);
            requestDTO.setProductUnavailableSizes(unavailableSizes);

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