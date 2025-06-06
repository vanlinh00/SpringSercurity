package com.example.SpringSecurity.service;


import com.example.SpringSecurity.entity.hibernate.Customer;
import com.example.SpringSecurity.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CustomerService {

    private final CustomerRepository customerRepository;

        public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.update(id, updatedCustomer);
    }

    public boolean deleteCustomer(Long id) {
        return customerRepository.delete(id);
    }
}
