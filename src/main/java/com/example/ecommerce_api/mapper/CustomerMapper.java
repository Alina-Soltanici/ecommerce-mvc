package com.example.ecommerce_api.mapper;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import com.example.ecommerce_api.entity.Customer;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    //Customer -> CustomerResponse
   CustomerResponse toDto(Customer customer);

    //CustomerRequest -> Customer
    Customer toEntity(CustomerRequest customerRequest);
}
