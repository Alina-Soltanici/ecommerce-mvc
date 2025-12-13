package com.example.ecommerce_api.mapper;

import com.example.ecommerce_api.dto.ProductRequest;
import com.example.ecommerce_api.dto.ProductResponse;
import com.example.ecommerce_api.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //ProductRequest -> Product
    Product toEntity(ProductRequest productRequest);

    //Product -> ProductResponse
    ProductResponse toDto(Product product);
}
