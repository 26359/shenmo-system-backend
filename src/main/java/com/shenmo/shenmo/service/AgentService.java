package com.shenmo.shenmo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.Agent;
import com.shenmo.shenmo.repository.AgentRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public String save(Agent agent) {
        agentRepository.save(agent);
        return "Agent saved successfully";
    }

    public List<Agent> findAll() {
        return agentRepository.findAll();
    }

    public Optional<Agent> findById(UUID id) {
        return agentRepository.findById(id);
    }

    public Optional<Agent> update(UUID id, Agent agent) {
        Optional<Agent> existing = agentRepository.findById(id);
        if (existing.isPresent()) {
            Agent a = existing.get();
            a.setFirstName(agent.getFirstName());
            a.setLastName(agent.getLastName());
            a.setEmail(agent.getEmail());
            a.setCounter(agent.getCounter());
            agentRepository.save(a);
            return Optional.of(a);
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        if (agentRepository.existsById(id)) {
            agentRepository.deleteById(id);
            return "Agent deleted successfully";
        }
        return "The agent with that id does not exist";
    }
}
