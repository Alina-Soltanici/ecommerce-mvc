package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.*;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse getProduct(Long id);

    List<ProductResponse> getProducts();

    ProductResponse updateProduct(Long id, ProductUpdateRequest updated);

    void deleteProduct(Long id);
}
