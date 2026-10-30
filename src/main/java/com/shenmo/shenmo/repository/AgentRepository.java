package com.shenmo.shenmo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {

    @Query("SELECT a FROM Agent a JOIN a.skills s WHERE LOWER(s.name) = LOWER(:name)")
    List<Agent> findBySkill(@Param("name") String name);
}
