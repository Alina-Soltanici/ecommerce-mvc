package com.example.ecommerce_api.exception.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        LocalDateTime time
) {}
