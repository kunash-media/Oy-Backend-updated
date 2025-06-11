package com.oy.oy_jewels.service.serviceImpl;

import com.oy.oy_jewels.dto.request.ProductCreateRequestDTO;
import com.oy.oy_jewels.dto.request.ProductDTO;
import com.oy.oy_jewels.dto.request.ProductPatchRequestDTO;
import com.oy.oy_jewels.entity.ProductEntity;
import com.oy.oy_jewels.mapper.ProductMapper;
import com.oy.oy_jewels.repository.ProductRepository;
import com.oy.oy_jewels.service.ProductService;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDTO createProduct(ProductCreateRequestDTO requestDTO) {
        // Check if product already exists
        Optional<ProductEntity> existProduct = productRepository.findByProductTitle(requestDTO.getProductTitle());

        if (existProduct.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with this title already exists");
        }

        try {
            validateProductRequest(requestDTO);
            ProductEntity productEntity = productMapper.toEntity(requestDTO);
            ProductEntity savedEntity = productRepository.save(productEntity);
            return productMapper.toDTO(savedEntity);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        try {
            List<ProductEntity> entities = productRepository.findAll();
            return productMapper.toDTOList(entities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve products: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        try {
            validateProductId(productId);
            ProductEntity entity = findProductEntityById(productId);
            return productMapper.toDTO(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve product with id " + productId + ": " + e.getMessage(), e);
        }
    }


    @Override
    public ProductDTO patchProduct(Long productId, ProductPatchRequestDTO patchRequest) {
        try {
            validateProductId(productId);

            ProductEntity existingEntity = findProductEntityById(productId);

            // Update only the fields that are present in the patch request
            if (patchRequest.getProductTitle() != null) {
                existingEntity.setProductTitle(patchRequest.getProductTitle());
            }
            if (patchRequest.getProductPrice() != null) {
                existingEntity.setProductPrice(patchRequest.getProductPrice());
            }
            if (patchRequest.getProductOldPrice() != null) {
                existingEntity.setProductOldPrice(patchRequest.getProductOldPrice());
            }
            if (patchRequest.isProductImagePresent()) {
                existingEntity.setProductImage(patchRequest.getProductImage());
            }
            if (patchRequest.isProductSubImagesPresent()) {
                existingEntity.setProductSubImages(patchRequest.getProductSubImages());
            }
            if (patchRequest.getProductDescription() != null) {
                existingEntity.setProductDescription(patchRequest.getProductDescription());
            }
            if (patchRequest.getProductFeatures() != null) {
                existingEntity.setProductFeatures(patchRequest.getProductFeatures());
            }
            if (patchRequest.getProductSizes() != null) {
                existingEntity.setProductSizes(patchRequest.getProductSizes());
            }
            if (patchRequest.getProductUnavailableSizes() != null) {
                existingEntity.setProductUnavailableSizes(patchRequest.getProductUnavailableSizes());
            }
            if (patchRequest.getProductCategory() != null) {
                existingEntity.setProductCategory(patchRequest.getProductCategory());
            }
            if (patchRequest.getProductStock() != null) {
                existingEntity.setProductStock(patchRequest.getProductStock());
            }
            if (patchRequest.getProductQuantity() != null) {
                existingEntity.setProductQuantity(patchRequest.getProductQuantity());
            }
            if (patchRequest.getShopBy() != null) {
                existingEntity.setShopBy(patchRequest.getShopBy());
            }
            if (patchRequest.getProductDiscount() != null) {
                existingEntity.setProductDiscount(patchRequest.getProductDiscount());
            }
            if (patchRequest.getProductCouponCode() != null) {
                existingEntity.setProductCouponCode(patchRequest.getProductCouponCode());
            }

            ProductEntity updatedEntity = productRepository.save(existingEntity);
            return productMapper.toDTO(updatedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to patch product with id " + productId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductCreateRequestDTO requestDTO) {
        try {
            validateProductId(productId);
            validateProductRequest(requestDTO);

            ProductEntity existingEntity = findProductEntityById(productId);
            productMapper.updateEntityFromDTO(existingEntity, requestDTO);

            ProductEntity updatedEntity = productRepository.save(existingEntity);
            return productMapper.toDTO(updatedEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product with id " + productId + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        try {
            validateProductId(productId);
            ProductEntity entity = findProductEntityById(productId);
            productRepository.delete(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product with id " + productId + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByTitle(String title) {
        try {
            if (!StringUtils.hasText(title)) {
                throw new IllegalArgumentException("Product title cannot be null or empty");
            }
            List<ProductEntity> entities = productRepository.findByProductTitleContainingIgnoreCase(title.trim());
            return productMapper.toDTOList(entities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to search products by title '" + title + "': " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long productId) {
        try {
            validateProductId(productId);
            return productRepository.existsById(productId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to check product existence with id " + productId + ": " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getProductCount() {
        try {
            return productRepository.count();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product count: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(String category) {
        try {
            if (!StringUtils.hasText(category)) {
                throw new IllegalArgumentException("Category cannot be null or empty");
            }
            List<ProductEntity> entities = productRepository.findByProductCategory(category.trim());
            return productMapper.toDTOList(entities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by category '" + category + "': " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByShopBy(String shopBy) {
        try {
            if (!StringUtils.hasText(shopBy)) {
                throw new IllegalArgumentException("ShopBy cannot be null or empty");
            }
            List<ProductEntity> entities = productRepository.findByShopBy(shopBy.trim());
            return productMapper.toDTOList(entities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get products by shopBy '" + shopBy + "': " + e.getMessage(), e);
        }
    }

    // Private helper methods
    private ProductEntity findProductEntityById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    }

    private void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
    }

    private void validateProductRequest(ProductCreateRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Product request cannot be null");
        }

        if (!StringUtils.hasText(requestDTO.getProductTitle())) {
            throw new IllegalArgumentException("Product title is required");
        }

        if (requestDTO.getProductPrice() == null || requestDTO.getProductPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price must be a non-negative value");
        }

        if (requestDTO.getProductOldPrice() != null && requestDTO.getProductOldPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product old price must be a non-negative value");
        }

        if (!StringUtils.hasText(requestDTO.getProductCategory())) {
            throw new IllegalArgumentException("Product category is required");
        }

        if (!StringUtils.hasText(requestDTO.getProductStock())) {
            throw new IllegalArgumentException("Product stock information is required");
        }

        if (requestDTO.getProductQuantity() == null || requestDTO.getProductQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity must be a non-negative value");
        }

        // Trim string fields
        requestDTO.setProductTitle(requestDTO.getProductTitle().trim());
        requestDTO.setProductCategory(requestDTO.getProductCategory().trim());
        requestDTO.setProductStock(requestDTO.getProductStock().trim());

        if (StringUtils.hasText(requestDTO.getProductDescription())) {
            requestDTO.setProductDescription(requestDTO.getProductDescription().trim());
        }
        if (StringUtils.hasText(requestDTO.getShopBy())) {
            requestDTO.setShopBy(requestDTO.getShopBy().trim());
        }
        if (StringUtils.hasText(requestDTO.getProductDiscount())) {
            requestDTO.setProductDiscount(requestDTO.getProductDiscount().trim());
        }
        if (StringUtils.hasText(requestDTO.getProductCouponCode())) {
            requestDTO.setProductCouponCode(requestDTO.getProductCouponCode().trim());
        }
    }
}