package com.example.ecommerce_api.service;

import com.example.ecommerce_api.dto.CustomerRequest;
import com.example.ecommerce_api.dto.CustomerResponse;
import com.example.ecommerce_api.entity.Customer;
import com.example.ecommerce_api.exception.ResourceNotFoundException;
import com.example.ecommerce_api.exception.EmailAlreadyExistsException;
import com.example.ecommerce_api.mapper.CustomerMapper;
import com.example.ecommerce_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        String email = customerRequest.getEmail();

        // Use findByEmail for detailed internal logging
        customerRepository.findByEmail(email).ifPresent(existingCustomer -> {

            // Detailed server-side logging (customers never see this)
            log.warn("Duplicate registration attempt blocked - " +
                    "Email {}, " +
                    "Existing customer ID {}, " +
                    "Existing name {} {}", email, existingCustomer.getId(), existingCustomer.getFirstName(), existingCustomer.getLastName());

            throw new EmailAlreadyExistsException("Email already registered. Try to define another email.");
        });


        try {
            Customer saved = customerRepository.save(customerMapper.toEntity(customerRequest));

            //Success registration
            log.info("Customer registration successful - ID: {}, Email: {}, Name: {} {}", saved.getId(), saved.getEmail(), saved.getFirstName(), saved.getLastName());

            return customerMapper.toDto(saved);

        } catch (DataIntegrityViolationException exception) {
            log.error("Race condition detected - duplicate email: {}", email, exception);
            throw new EmailAlreadyExistsException("Email already registered.");
        }
    }


    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new ResourceNotFoundException("Customer not found!");
        });


        log.info("Customer retrieved - ID: {}, Name: {} {}, Email: {}",
                foundedCustomer.getId(),
                foundedCustomer.getFirstName(),
                foundedCustomer.getLastName(),
                foundedCustomer.getEmail());
        return customerMapper.toDto(foundedCustomer);
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
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new ResourceNotFoundException("Customer not found!");
        });

        if(updated.getFirstName() != null && !updated.getFirstName().isBlank()) {
            log.info("Set first name: {} for user with ID {}", updated.getFirstName(), id);
            foundedCustomer.setFirstName(updated.getFirstName());
        }

        if(updated.getLastName() != null && !updated.getLastName().isBlank()) {
            log.info("Set last name: {} for user with ID {}", updated.getLastName(), id);
            foundedCustomer.setLastName(updated.getLastName());
        }

        if(updated.getPhoneNumber() != null && !updated.getPhoneNumber().isBlank()) {
            log.info("Set phone number: {} for user with ID {}", updated.getPhoneNumber(), id);
            foundedCustomer.setPhoneNumber(updated.getPhoneNumber());
        }

        if(updated.getAddress() != null && !updated.getAddress().isBlank()){
            log.info("Set address: {} for user with ID {}", updated.getAddress(), id);
            foundedCustomer.setAddress(updated.getAddress());
        }


        Customer savedCustomer = customerRepository.save(foundedCustomer);
        log.info("Customer with ID {} was updated successfully", id);
        return customerMapper.toDto(savedCustomer);
    }


    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new ResourceNotFoundException("Customer not found!");
        });

        log.info("Customer with ID {} was deleted successfully", id);
        customerRepository.delete(foundedCustomer);
    }
}
