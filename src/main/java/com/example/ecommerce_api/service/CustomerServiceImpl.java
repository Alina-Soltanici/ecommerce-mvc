package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import com.example.ecommerce_api.entity.Customer;
import com.example.ecommerce_api.exception.PhoneAlreadyExistsException;
import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.exception.EmailAlreadyExistsException;
import com.example.ecommerce_api.mapper.CustomerMapper;
import com.example.ecommerce_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        String emailHash = DigestUtils.sha256Hex(customerRequest.getEmail());

        try {
            Customer saved = customerRepository.save(customerMapper.toEntity(customerRequest));
            log.info("Customer registration successful - ID: {}, Email hash: {}",
                    saved.getId(), emailHash);
            return customerMapper.toDto(saved);

        } catch (DataIntegrityViolationException exception) {
            String message = exception.getMostSpecificCause().getMessage().toLowerCase();

            if (message.contains("email") || message.contains("uk_email")) {
                log.warn("Duplicate email registration attempt - Email hash: {}", emailHash);
                throw new EmailAlreadyExistsException("Email already registered");

            } else if (message.contains("phone") || message.contains("uk_phone")) {
                log.warn("Duplicate phone registration attempt - Email hash: {}", emailHash);
                throw new PhoneAlreadyExistsException("Phone number already registered");

            } else {
                log.error("Unexpected data integrity violation during registration - Email hash: {}",
                        emailHash, exception);
                throw new RuntimeException("Registration failed due to duplicate data", exception);
            }
        }

    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new ResourceNotFoundException("Customer not found!");
        });


        log.info("Customer retrieved - ID: {}, Name: {} {}, Email: {}",
                foundCustomer.getId(),
                foundCustomer.getFirstName(),
                foundCustomer.getLastName(),
                foundCustomer.getEmail());
        return customerMapper.toDto(foundCustomer);
    }


    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .toList();
    }


    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest updated) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new ResourceNotFoundException("Customer not found!");
        });

        if(updated.getFirstName() != null && !updated.getFirstName().isBlank()) {
            log.info("Set first name: {} for user with ID {}", updated.getFirstName(), id);
            foundCustomer.setFirstName(updated.getFirstName());
        }

        if(updated.getLastName() != null && !updated.getLastName().isBlank()) {
            log.info("Set last name: {} for user with ID {}", updated.getLastName(), id);
            foundCustomer.setLastName(updated.getLastName());
        }

        if(updated.getPhoneNumber() != null && !updated.getPhoneNumber().isBlank()) {
            log.info("Set phone number: {} for user with ID {}", updated.getPhoneNumber(), id);
            foundCustomer.setPhoneNumber(updated.getPhoneNumber());
        }

        if(updated.getAddress() != null && !updated.getAddress().isBlank()){
            log.info("Set address: {} for user with ID {}", updated.getAddress(), id);
            foundCustomer.setAddress(updated.getAddress());
        }


        Customer savedCustomer = customerRepository.save(foundCustomer);
        log.info("Customer with ID {} was updated successfully", id);
        return customerMapper.toDto(savedCustomer);
    }


    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer foundCustomer = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new ResourceNotFoundException("Customer not found!");
        });

        log.info("Customer with ID {} was deleted successfully", id);
        customerRepository.delete(foundCustomer);
    }
}
