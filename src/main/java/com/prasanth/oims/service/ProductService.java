package com.prasanth.oims.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.prasanth.oims.dto.ProductRequestDTO;
import com.prasanth.oims.dto.ProductResponseDTO;

public interface ProductService {

    public ProductResponseDTO getProductById(Long productId);
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);
    public void deleteProduct(Long productId);
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO);
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable);

}
