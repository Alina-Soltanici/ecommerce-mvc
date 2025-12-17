package com.example.ecommerce_api.dto;

import com.example.ecommerce_api.entity.enums.Color;
import com.example.ecommerce_api.entity.enums.Size;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String name;
    private String description;
    private Color color;
    private Size size;

    @Positive(message = "Price must be greater than zero")
    private Double price;
}
