package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public CustomerResponse getCustomer(Long id) {
        return null;
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        return List.of();
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest updated) {
        return null;
    }

    @Override
    public void deleteCustomer(Long id) {

    }
}
