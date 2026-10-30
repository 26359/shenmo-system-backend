package com.shenmo.shenmo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shenmo.shenmo.model.ServiceNote;
import com.shenmo.shenmo.service.ServiceNoteService;

@RestController
@RequestMapping("/api/service-notes")
public class ServiceNoteController {

    @Autowired
    private ServiceNoteService serviceNoteService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam UUID serviceRequestId, @RequestBody ServiceNote serviceNote) {
        String message = serviceNoteService.save(serviceNote, serviceRequestId);
        if ("Service note saved successfully".equals(message)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        if ("Service notes are only for RESOLVED service requests".equals(message)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/customer/{customerCode}")
    public ResponseEntity<List<ServiceNote>> byCustomer(@PathVariable String customerCode) {
        return ResponseEntity.ok(serviceNoteService.findByCustomerCode(customerCode));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = serviceNoteService.delete(id);
        if ("Service note deleted successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
