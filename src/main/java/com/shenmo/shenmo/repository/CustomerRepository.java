package com.shenmo.shenmo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
