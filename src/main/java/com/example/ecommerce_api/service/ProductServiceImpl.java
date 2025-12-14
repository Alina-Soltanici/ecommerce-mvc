package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.ProductRequest;
import com.example.ecommerce_api.dto.ProductResponse;
import com.example.ecommerce_api.entity.Product;
import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.mapper.ProductMapper;
import com.example.ecommerce_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productRepository.save(productMapper.toEntity(productRequest));
        log.info("Product with id {} added successfully", product.getId());
        return productMapper.toDto(product);
    }


    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product with id {} not found", id);
           return new ResourceNotFoundException("Product not found");
        });

        return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return List.of();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest updated) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
