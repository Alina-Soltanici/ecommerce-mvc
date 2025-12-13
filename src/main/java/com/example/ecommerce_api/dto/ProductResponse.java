package com.example.ecommerce_api.dto;

import com.example.ecommerce_api.entity.enums.Color;
import com.example.ecommerce_api.entity.enums.Size;
import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Color color;
    private Size size;
    private Double price;
}
