package com.example.ecommerce_api.controller;

import com.example.ecommerce_api.dto.ProductRequest;
import com.example.ecommerce_api.dto.ProductResponse;
import com.example.ecommerce_api.entity.Product;
import com.example.ecommerce_api.service.ProductService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse created = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
