package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.ProductRequest;
import com.example.ecommerce_api.dto.ProductResponse;
import com.example.ecommerce_api.entity.Product;
import com.example.ecommerce_api.exception.DuplicateResourceException;
import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.mapper.ProductMapper;
import com.example.ecommerce_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        try {
            Product product = productRepository.save(productMapper.toEntity(productRequest));
            log.info("Product created successfully with id: {} and SKU: {}",
                    product.getId(), product.getSku());
            return productMapper.toDto(product);

        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage().toLowerCase();
            if(message.contains("sku") || message.contains("uk_sku")) {
                log.warn("Duplicate SKU attempt");
                throw new DuplicateResourceException("Product SKU already exists");
            }

            log.error("Unexpected data integrity violation during product creation", e);
            throw new RuntimeException("Product creation failed", e);
        }
    }


    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product with id {} not found", id);
            return new ResourceNotFoundException("Product not found");
        });

        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProducts() {
       List<ProductResponse> products = productRepository.findAll().stream().map(productMapper::toDto).toList();
        log.info("Successfully retrieved {} products", products.size());

        return products;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest updated) {
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Product with id {} not found", id);
            return new ResourceNotFoundException("Product not found");
        });



    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            log.warn("Product with id {} not found", id);
            throw new ResourceNotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }
}
