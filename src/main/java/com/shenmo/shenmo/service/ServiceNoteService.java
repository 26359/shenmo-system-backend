package com.shenmo.shenmo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.RequestStatus;
import com.shenmo.shenmo.model.ServiceNote;
import com.shenmo.shenmo.model.ServiceRequest;
import com.shenmo.shenmo.repository.ServiceNoteRepository;
import com.shenmo.shenmo.repository.ServiceRequestRepository;

@Service
public class ServiceNoteService {

    @Autowired
    private ServiceNoteRepository serviceNoteRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public String save(ServiceNote serviceNote, UUID serviceRequestId) {
        Optional<ServiceRequest> requestOpt = serviceRequestRepository.findById(serviceRequestId);
        if (!requestOpt.isPresent()) {
            return "The service request with that id does not exist";
        }
        ServiceRequest request = requestOpt.get();
        if (request.getStatus() != RequestStatus.RESOLVED) {
            return "Service notes are only for RESOLVED service requests";
        }
        serviceNote.setWrittenDate(LocalDate.now());
        serviceNote.setServiceRequest(request);
        serviceNoteRepository.save(serviceNote);
        return "Service note saved successfully";
    }

    public List<ServiceNote> findByCustomerCode(String customerCode) {
        return serviceNoteRepository.findByCustomerCode(customerCode);
    }

    public String delete(UUID id) {
        if (serviceNoteRepository.existsById(id)) {
            serviceNoteRepository.deleteById(id);
            return "Service note deleted successfully";
        }
        return "The service note with that id does not exist";
    }
}
