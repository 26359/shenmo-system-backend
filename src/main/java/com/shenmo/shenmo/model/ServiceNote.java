package com.shenmo.shenmo.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_note")
public class ServiceNote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String actionTaken;

    private String details;

    private int minutesSpent;

    private LocalDate writtenDate;

    @ManyToOne
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;

    public ServiceNote() {
    }

    public ServiceNote(String actionTaken, String details, int minutesSpent) {
        this.actionTaken = actionTaken;
        this.details = details;
        this.minutesSpent = minutesSpent;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }

    public void setMinutesSpent(int minutesSpent) {
        this.minutesSpent = minutesSpent;
    }

    public LocalDate getWrittenDate() {
        return writtenDate;
    }

    public void setWrittenDate(LocalDate writtenDate) {
        this.writtenDate = writtenDate;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }
}
