package com.shenmo.shenmo.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.RequestStatus;
import com.shenmo.shenmo.repository.AgentRepository;
import com.shenmo.shenmo.repository.CounterRepository;
import com.shenmo.shenmo.repository.CustomerRepository;
import com.shenmo.shenmo.repository.ServiceNoteRepository;
import com.shenmo.shenmo.repository.ServiceRequestRepository;

@Service
public class DashboardService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private ServiceNoteRepository serviceNoteRepository;

    public Map<String, Long> summary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("customers", customerRepository.count());
        summary.put("agents", agentRepository.count());
        summary.put("freeCounters", (long) counterRepository.findFreeCounters().size());
        summary.put("requestsToday", serviceRequestRepository.countByRequestDate(LocalDate.now()));
        summary.put("opened", serviceRequestRepository.countByStatus(RequestStatus.OPENED));
        summary.put("serviceNotes", serviceNoteRepository.count());
        return summary;
    }
}
