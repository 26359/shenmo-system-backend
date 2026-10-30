package com.shenmo.shenmo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {
}
