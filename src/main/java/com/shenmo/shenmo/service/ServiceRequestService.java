package com.shenmo.shenmo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shenmo.shenmo.model.Agent;
import com.shenmo.shenmo.model.Customer;
import com.shenmo.shenmo.model.RequestStatus;
import com.shenmo.shenmo.model.ServiceRequest;
import com.shenmo.shenmo.repository.ServiceNoteRepository;
import com.shenmo.shenmo.repository.ServiceRequestRepository;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceNoteRepository serviceNoteRepository;

    public String save(ServiceRequest serviceRequest) {
        LocalDate today = LocalDate.now();
        if (serviceRequest.getRequestDate() != null && serviceRequest.getRequestDate().isBefore(today)) {
            return "Service request date cannot be in the past";
        }
        Agent agent = serviceRequest.getAgent();
        if (agent != null && serviceRequest.getRequestDate() != null
                && serviceRequestRepository.existsByAgentIdAndRequestDateAndStatusNot(agent.getId(),
                        serviceRequest.getRequestDate(), RequestStatus.CLOSED_UNRESOLVED)) {
            return "Agent already has a request on that date";
        }
        serviceRequest.setStatus(RequestStatus.OPENED);
        serviceRequestRepository.save(serviceRequest);
        return "Service request saved successfully";
    }

    public List<ServiceRequest> findAll() {
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> findByStatus(RequestStatus status) {
        return serviceRequestRepository.findByStatusOrderByRequestDateAsc(status);
    }

    public List<ServiceRequest> findBetween(LocalDate start, LocalDate end) {
        return serviceRequestRepository.findByRequestDateBetweenOrderByRequestDateAsc(start, end);
    }

    public List<Customer> findCustomersOfAgent(UUID agentId) {
        return serviceRequestRepository.findCustomersOfAgent(agentId);
    }

    public Page<ServiceRequest> findAll(Pageable pageable) {
        return serviceRequestRepository.findAll(pageable);
    }

    public List<Object[]> statsByStatus() {
        return serviceRequestRepository.statsByStatus();
    }

    @Transactional
    public String cancelDay(UUID agentId, LocalDate date) {
        int count = serviceRequestRepository.cancelDay(RequestStatus.CLOSED_UNRESOLVED, agentId, date, RequestStatus.RESOLVED);
        return count + " service requests cancelled";
    }

    public String updateStatus(UUID id, RequestStatus status) {
        Optional<ServiceRequest> existing = serviceRequestRepository.findById(id);
        if (!existing.isPresent()) {
            return "The service request with that id does not exist";
        }
        ServiceRequest sr = existing.get();
        RequestStatus current = sr.getStatus();
        boolean valid = (current == RequestStatus.OPENED
                && (status == RequestStatus.IN_PROGRESS || status == RequestStatus.CLOSED_UNRESOLVED))
                || (current == RequestStatus.IN_PROGRESS
                && (status == RequestStatus.RESOLVED || status == RequestStatus.CLOSED_UNRESOLVED));
        if (!valid) {
            return "Cannot change status from " + current + " to " + status;
        }
        sr.setStatus(status);
        serviceRequestRepository.save(sr);
        return "Service request status updated successfully";
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
        Optional<ServiceRequest> existing = serviceRequestRepository.findById(id);
        if (!existing.isPresent()) {
            return "The service request with that id does not exist";
        }
        ServiceRequest sr = existing.get();
        if (serviceNoteRepository.existsByServiceRequest(sr)) {
            return "That service request has service notes, delete them first";
        }
        serviceRequestRepository.deleteById(id);
        return "Service request deleted successfully";
    }
}
