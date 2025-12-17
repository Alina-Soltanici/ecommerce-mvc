package com.example.ecommerce_api.entity;

import com.example.ecommerce_api.entity.enums.Color;
import com.example.ecommerce_api.entity.enums.Size;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products", schema = "ecommerce")
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sku;
    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_size")
    private Size size;

    private Double price;
}
