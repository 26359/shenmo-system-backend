package com.shenmo.shenmo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.Agent;
import com.shenmo.shenmo.model.Counter;
import com.shenmo.shenmo.model.Skill;
import com.shenmo.shenmo.repository.AgentRepository;
import com.shenmo.shenmo.repository.CounterRepository;
import com.shenmo.shenmo.repository.SkillRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private SkillRepository skillRepository;

    public String save(Agent agent) {
        agentRepository.save(agent);
        return "Agent saved successfully";
    }

    public List<Agent> findAll() {
        return agentRepository.findAll();
    }

    public List<Agent> findBySkill(String name) {
        return agentRepository.findBySkill(name);
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

    public String addSkillToAgent(UUID agentId, UUID skillId) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        if (!agentOpt.isPresent()) {
            return "The agent with that id does not exist";
        }
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (!skillOpt.isPresent()) {
            return "That skill does not exist";
        }
        Agent agent = agentOpt.get();
        Skill skill = skillOpt.get();
        if (agent.getSkills() == null) {
            agent.setSkills(new ArrayList<>());
        }
        if (agent.getSkills().contains(skill)) {
            return "The agent already has that skill";
        }
        agent.getSkills().add(skill);
        agentRepository.save(agent);
        return "Skill added to the agent";
    }

    public String changeAgentCounter(UUID agentId, int counterNumber) {
        Optional<Agent> agentOpt = agentRepository.findById(agentId);
        if (!agentOpt.isPresent()) {
            return "The agent with that id does not exist";
        }
        Optional<Counter> counterOpt = counterRepository.findByCounterNumber(counterNumber);
        if (!counterOpt.isPresent()) {
            return "The counter with that counterNumber does not exist";
        }
        Counter counter = counterOpt.get();
        if (agentRepository.existsByCounterAndIdNot(counter, agentId)) {
            return "That counter is taken by another agent";
        }
        Agent agent = agentOpt.get();
        agent.setCounter(counter);
        agentRepository.save(agent);
        return "Counter changed successfully";
    }
}
