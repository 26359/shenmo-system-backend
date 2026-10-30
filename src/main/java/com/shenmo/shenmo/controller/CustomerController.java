package com.shenmo.shenmo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shenmo.shenmo.model.Customer;
import com.shenmo.shenmo.service.AgentService;
import com.shenmo.shenmo.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AgentService agentService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        String message = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Customer>> byLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(customerService.findByLastName(lastName));
    }

    @GetMapping("/of-agent/{agentId}")
    public ResponseEntity<?> customersOfAgent(@PathVariable UUID agentId) {
        if (!agentService.findById(agentId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The agent with that id does not exist");
        }
        return ResponseEntity.ok(customerService.findCustomersOfAgent(agentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Customer customer) {
        Optional<Customer> updated = customerService.update(id, customer);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The customer with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = customerService.delete(id);
        if ("Customer deleted successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
