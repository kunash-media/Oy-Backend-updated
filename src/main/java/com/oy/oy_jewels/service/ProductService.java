package com.oy.oy_jewels.service;

import com.oy.oy_jewels.dto.request.ProductCreateRequestDTO;
import com.oy.oy_jewels.dto.request.ProductDTO;
import com.oy.oy_jewels.dto.request.ProductPatchRequestDTO;
import com.oy.oy_jewels.entity.ProductEntity;

import java.util.List;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductCreateRequestDTO requestDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long productId);
    ProductDTO updateProduct(Long productId, ProductCreateRequestDTO requestDTO);
    void deleteProduct(Long productId);
    List<ProductDTO> getProductsByTitle(String title);
    boolean existsById(Long productId);
    long getProductCount();
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsByShopBy(String shopBy);

    //pacth api
    public ProductDTO patchProduct(Long productId, ProductPatchRequestDTO patchRequest);
}