package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import com.example.ecommerce_api.dto.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse getCustomer(Long id);

    List<CustomerResponse> getCustomers();

    CustomerResponse updateCustomer(Long id, CustomerUpdateRequest updated);

    void deleteCustomer(Long id);
}
