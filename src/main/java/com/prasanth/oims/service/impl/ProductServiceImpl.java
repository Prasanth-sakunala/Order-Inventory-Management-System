package com.prasanth.oims.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prasanth.oims.dto.ProductRequestDTO;
import com.prasanth.oims.dto.ProductResponseDTO;
import com.prasanth.oims.entity.Product;
import com.prasanth.oims.repository.ProductRepository;
import com.prasanth.oims.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository  productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value = "productById", key ="#productId")
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalStateException("Product not found"));
        return convertToResponseDTO(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = "productById", allEntries = true)
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product saveProduct = productRepository.save(convertToEntity(new Product(),productRequestDTO));
        return convertToResponseDTO(saveProduct); 
    }

    @Override
    @Transactional
    @CacheEvict(value = "productById", allEntries = true)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new IllegalStateException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = "productById", allEntries = true)
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new IllegalStateException("Product not found"));
        Product product =productRepository.save(convertToEntity(existingProduct,productRequestDTO));
        return convertToResponseDTO(product);
        
    }

    @Override
    @Cacheable(value = "allProducts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> products=productRepository.findAll(pageable);
        Page<ProductResponseDTO> responseDTOs = products.map(product -> {
            return convertToResponseDTO(product);
        });
        return responseDTOs;
    }

    private ProductResponseDTO convertToResponseDTO(Product product){
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setQuantity(product.getQuantity());
        return responseDTO;
    }

    private Product convertToEntity(Product product,ProductRequestDTO productRequestDTO){
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        return product;
    }

}
