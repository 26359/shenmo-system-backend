package com.shenmo.shenmo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shenmo.shenmo.model.RequestStatus;
import com.shenmo.shenmo.model.ServiceRequest;
import com.shenmo.shenmo.service.AgentService;
import com.shenmo.shenmo.service.ServiceRequestService;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @Autowired
    private AgentService agentService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ServiceRequest serviceRequest) {
        String message = serviceRequestService.save(serviceRequest);
        if ("Service request saved successfully".equals(message)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        if ("Service request date cannot be in the past".equals(message)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceRequest>> getAll() {
        return ResponseEntity.ok(serviceRequestService.findAll());
    }

    @GetMapping("/of-agent/{agentId}")
    public ResponseEntity<?> customersOfAgent(@PathVariable UUID agentId) {
        if (!agentService.findById(agentId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The agent with that id does not exist");
        }
        return ResponseEntity.ok(serviceRequestService.findCustomersOfAgent(agentId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam RequestStatus status) {
        String message = serviceRequestService.updateStatus(id, status);
        if ("Service request status updated successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        if (message.startsWith("Cannot change status from")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ServiceRequest>> byStatus(@RequestParam RequestStatus status) {
        return ResponseEntity.ok(serviceRequestService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<ServiceRequest>> between(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(serviceRequestService.findBetween(startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<ServiceRequest> serviceRequest = serviceRequestService.findById(id);
        if (serviceRequest.isPresent()) {
            return ResponseEntity.ok(serviceRequest.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The service request with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody ServiceRequest serviceRequest) {
        Optional<ServiceRequest> updated = serviceRequestService.update(id, serviceRequest);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The service request with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = serviceRequestService.delete(id);
        if ("Service request deleted successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
