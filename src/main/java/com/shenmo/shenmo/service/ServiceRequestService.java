package com.shenmo.shenmo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.ServiceRequest;
import com.shenmo.shenmo.repository.ServiceRequestRepository;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public String save(ServiceRequest serviceRequest) {
        serviceRequestRepository.save(serviceRequest);
        return "Service request saved successfully";
    }

    public List<ServiceRequest> findAll() {
        return serviceRequestRepository.findAll();
    }

    public Optional<ServiceRequest> findById(UUID id) {
        return serviceRequestRepository.findById(id);
    }

    public Optional<ServiceRequest> update(UUID id, ServiceRequest serviceRequest) {
        Optional<ServiceRequest> existing = serviceRequestRepository.findById(id);
        if (existing.isPresent()) {
            ServiceRequest sr = existing.get();
            sr.setRequestDate(serviceRequest.getRequestDate());
            sr.setSubject(serviceRequest.getSubject());
            sr.setCustomer(serviceRequest.getCustomer());
            sr.setAgent(serviceRequest.getAgent());
            // status is handled by the status endpoint, not here
            serviceRequestRepository.save(sr);
            return Optional.of(sr);
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        if (serviceRequestRepository.existsById(id)) {
            serviceRequestRepository.deleteById(id);
            return "Service request deleted successfully";
        }
        return "The service request with that id does not exist";
    }
}
