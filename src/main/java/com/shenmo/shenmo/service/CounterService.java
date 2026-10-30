package com.shenmo.shenmo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.Counter;
import com.shenmo.shenmo.repository.CounterRepository;

@Service
public class CounterService {

    @Autowired
    private CounterRepository counterRepository;

    public String save(Counter counter) {
        counterRepository.save(counter);
        return "Counter saved successfully";
    }

    public List<Counter> findAll() {
        return counterRepository.findAll();
    }

    public List<Counter> findFreeCounters() {
        return counterRepository.findFreeCounters();
    }

    public Optional<Counter> findById(UUID id) {
        return counterRepository.findById(id);
    }

    public Optional<Counter> update(UUID id, Counter counter) {
        Optional<Counter> existing = counterRepository.findById(id);
        if (existing.isPresent()) {
            Counter c = existing.get();
            c.setCounterNumber(counter.getCounterNumber());
            c.setName(counter.getName());
            counterRepository.save(c);
            return Optional.of(c);
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        if (counterRepository.existsById(id)) {
            counterRepository.deleteById(id);
            return "Counter deleted successfully";
        }
        return "The counter with that id does not exist";
    }
}
