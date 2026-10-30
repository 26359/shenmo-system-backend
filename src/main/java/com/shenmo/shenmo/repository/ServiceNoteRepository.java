package com.shenmo.shenmo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.ServiceNote;
import com.shenmo.shenmo.model.ServiceRequest;

@Repository
public interface ServiceNoteRepository extends JpaRepository<ServiceNote, UUID> {

    boolean existsByServiceRequest(ServiceRequest serviceRequest);

    @Query("SELECT n FROM ServiceNote n WHERE n.serviceRequest.customer.customerCode = :customerCode ORDER BY n.writtenDate DESC")
    List<ServiceNote> findByCustomerCode(@Param("customerCode") String customerCode);
}
