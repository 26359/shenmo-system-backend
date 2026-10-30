package com.shenmo.shenmo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.Customer;
import com.shenmo.shenmo.repository.CustomerRepository;
import com.shenmo.shenmo.repository.ServiceRequestRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public String save(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<Customer> findCustomersOfAgent(UUID agentId) {
        return serviceRequestRepository.findCustomersOfAgent(agentId);
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> update(UUID id, Customer customer) {
        Optional<Customer> existing = customerRepository.findById(id);
        if (existing.isPresent()) {
            Customer c = existing.get();
            c.setFirstName(customer.getFirstName());
            c.setLastName(customer.getLastName());
            c.setCustomerCode(customer.getCustomerCode());
            c.setPhone(customer.getPhone());
            c.setEmail(customer.getEmail());
            customerRepository.save(c);
            return Optional.of(c);
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return "Customer deleted successfully";
        }
        return "The customer with that id does not exist";
    }
}
