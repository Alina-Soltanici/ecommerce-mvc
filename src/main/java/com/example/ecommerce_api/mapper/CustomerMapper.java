package com.example.ecommerce_api.mapper;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import com.example.ecommerce_api.entity.Customer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    //Customer -> CustomerResponse
    public CustomerResponse toDto(Customer customer);

    //CustomerRequest -> Customer
    public Customer toEntity(CustomerRequest customerRequest);
}
