package com.oy.oy_jewels.mapper;

import com.oy.oy_jewels.dto.request.ProductCreateRequestDTO;
import com.oy.oy_jewels.dto.request.ProductDTO;
import com.oy.oy_jewels.entity.ProductEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    // Convert Entity to DTO
    public ProductDTO toDTO(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ProductDTO(
                entity.getProductId(),
                entity.getProductTitle(),
                entity.getProductPrice(),
                entity.getProductOldPrice(),
                entity.getProductImage(),
                entity.getProductSubImages(),
                entity.getProductDescription(),
                entity.getProductFeatures(),
                entity.getProductSizes(),
                entity.getProductUnavailableSizes(),
                entity.getProductCategory(),
                entity.getProductStock(),
                entity.getProductQuantity(),
                entity.getShopBy(),
                entity.getProductDiscount(),
                entity.getProductCouponCode()
        );
    }

    // Convert DTO to Entity
    public ProductEntity toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setProductId(dto.getProductId());
        entity.setProductTitle(dto.getProductTitle());
        entity.setProductPrice(dto.getProductPrice());
        entity.setProductOldPrice(dto.getProductOldPrice());
        entity.setProductImage(dto.getProductImage());
        entity.setProductSubImages(dto.getProductSubImages());
        entity.setProductDescription(dto.getProductDescription());
        entity.setProductFeatures(dto.getProductFeatures());
        entity.setProductSizes(dto.getProductSizes());
        entity.setProductUnavailableSizes(dto.getProductUnavailableSizes());
        entity.setProductCategory(dto.getProductCategory());
        entity.setProductStock(dto.getProductStock());

        return entity;
    }

    // Convert CreateRequestDTO to Entity
    public ProductEntity toEntity(ProductCreateRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setProductTitle(requestDTO.getProductTitle());
        entity.setProductPrice(requestDTO.getProductPrice());
        entity.setProductOldPrice(requestDTO.getProductOldPrice());
        entity.setProductImage(requestDTO.getProductImage());
        entity.setProductSubImages(requestDTO.getProductSubImages());
        entity.setProductDescription(requestDTO.getProductDescription());
        entity.setProductFeatures(requestDTO.getProductFeatures());
        entity.setProductSizes(requestDTO.getProductSizes());
        entity.setProductUnavailableSizes(requestDTO.getProductUnavailableSizes());
        entity.setProductCategory(requestDTO.getProductCategory());
        entity.setProductStock(requestDTO.getProductStock());
        entity.setProductQuantity(requestDTO.getProductQuantity());
        entity.setShopBy(requestDTO.getShopBy());
        entity.setProductDiscount(requestDTO.getProductDiscount());
        entity.setProductCouponCode(requestDTO.getProductCouponCode());

        return entity;
    }


    // Convert List of Entities to List of DTOs
    public List<ProductDTO> toDTOList(List<ProductEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update existing entity with new data
    public void updateEntityFromDTO(ProductEntity existingEntity, ProductCreateRequestDTO requestDTO) {
        if (existingEntity == null || requestDTO == null) {
            return;
        }

        existingEntity.setProductTitle(requestDTO.getProductTitle());
        existingEntity.setProductPrice(requestDTO.getProductPrice());
        existingEntity.setProductOldPrice(requestDTO.getProductOldPrice());
        existingEntity.setProductImage(requestDTO.getProductImage());
        existingEntity.setProductSubImages(requestDTO.getProductSubImages());
        existingEntity.setProductDescription(requestDTO.getProductDescription());
        existingEntity.setProductFeatures(requestDTO.getProductFeatures());
        existingEntity.setProductSizes(requestDTO.getProductSizes());
        existingEntity.setProductUnavailableSizes(requestDTO.getProductUnavailableSizes());
        existingEntity.setProductCategory(requestDTO.getProductCategory());
        existingEntity.setProductStock(requestDTO.getProductStock());
        existingEntity.setProductQuantity(requestDTO.getProductQuantity());
        existingEntity.setShopBy(requestDTO.getShopBy());
        existingEntity.setProductDiscount(requestDTO.getProductDiscount());
        existingEntity.setProductCouponCode(requestDTO.getProductCouponCode());
    }
}