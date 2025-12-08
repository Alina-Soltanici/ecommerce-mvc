package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import com.example.ecommerce_api.entity.Customer;
import com.example.ecommerce_api.exception.CustomerNotFoundException;
import com.example.ecommerce_api.exception.EmailAlreadyExistsException;
import com.example.ecommerce_api.mapper.CustomerMapper;
import com.example.ecommerce_api.repository.CustomerRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {

        if(customerRepository.existsByEmail(customerRequest.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists.Try to define another email!");
        }

       Customer saved = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customerMapper.toCustomerResponse(saved);
    }


    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));
        return customerMapper.toCustomerResponse(foundedCustomer);
    }


    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }


    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest updated) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        if(!updated.getFirstName().isBlank()) {
            foundedCustomer.setFirstName(updated.getFirstName());
        }

        if(!updated.getLastName().isBlank()) {
            foundedCustomer.setLastName(updated.getLastName());
        }

        if(!updated.getPhoneNumber().isBlank()) {
            foundedCustomer.setPhoneNumber(updated.getPhoneNumber());
        }

        if(!updated.getAddress().isBlank()){
            foundedCustomer.setAddress(updated.getAddress());
        }


        Customer savedCustomer = customerRepository.save(foundedCustomer);
        return customerMapper.toCustomerResponse(savedCustomer);
    }


    @Override
    public void deleteCustomer(Long id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(foundedCustomer);
    }
}
