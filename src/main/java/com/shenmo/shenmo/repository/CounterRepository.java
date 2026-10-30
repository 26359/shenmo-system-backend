package com.shenmo.shenmo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, UUID> {

    @Query("SELECT c FROM Counter c WHERE NOT EXISTS (SELECT a FROM Agent a WHERE a.counter = c) ORDER BY c.counterNumber")
    List<Counter> findFreeCounters();

    Optional<Counter> findByCounterNumber(int counterNumber);
}
