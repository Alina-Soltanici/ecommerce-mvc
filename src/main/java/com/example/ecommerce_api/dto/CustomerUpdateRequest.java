package com.example.ecommerce_api.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerUpdateRequest {
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;

    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    private String lastName;

    private String phoneNumber;
    private String address;
}
