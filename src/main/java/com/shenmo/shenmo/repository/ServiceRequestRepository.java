package com.shenmo.shenmo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.Customer;
import com.shenmo.shenmo.model.RequestStatus;
import com.shenmo.shenmo.model.ServiceRequest;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {

    List<ServiceRequest> findByStatusOrderByRequestDateAsc(RequestStatus status);

    List<ServiceRequest> findByRequestDateBetweenOrderByRequestDateAsc(LocalDate start, LocalDate end);

    @Query("SELECT DISTINCT sr.customer FROM ServiceRequest sr WHERE sr.agent.id = :agentId")
    List<Customer> findCustomersOfAgent(@Param("agentId") UUID agentId);

    boolean existsByAgentIdAndRequestDateAndStatusNot(UUID agentId, LocalDate requestDate, RequestStatus status);

    @Query("SELECT sr.status, COUNT(sr) FROM ServiceRequest sr GROUP BY sr.status")
    List<Object[]> statsByStatus();

    @Modifying
    @Query("UPDATE ServiceRequest sr SET sr.status = :status WHERE sr.agent.id = :agentId AND sr.requestDate = :date AND sr.status <> 'RESOLVED'")
    int cancelDay(@Param("status") RequestStatus status, @Param("agentId") UUID agentId, @Param("date") LocalDate date);
}
