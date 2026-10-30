package com.shenmo.shenmo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shenmo.shenmo.model.Agent;
import com.shenmo.shenmo.service.AgentService;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Agent agent) {
        String message = agentService.save(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAll() {
        return ResponseEntity.ok(agentService.findAll());
    }

    @GetMapping("/by-skill")
    public ResponseEntity<List<Agent>> bySkill(@RequestParam String name) {
        return ResponseEntity.ok(agentService.findBySkill(name));
    }

    @PutMapping("/{agentId}/skills/{skillId}")
    public ResponseEntity<?> addSkill(@PathVariable UUID agentId, @PathVariable UUID skillId) {
        String message = agentService.addSkillToAgent(agentId, skillId);
        if ("Skill added to the agent".equals(message)) {
            return ResponseEntity.ok(message);
        }
        if ("The agent already has that skill".equals(message)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @PatchMapping("/{agentId}/counter")
    public ResponseEntity<?> changeCounter(@PathVariable UUID agentId, @RequestParam int counterNumber) {
        String message = agentService.changeAgentCounter(agentId, counterNumber);
        if ("Counter changed successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        if ("That counter is taken by another agent".equals(message)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Agent> agent = agentService.findById(id);
        if (agent.isPresent()) {
            return ResponseEntity.ok(agent.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The agent with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Agent agent) {
        Optional<Agent> updated = agentService.update(id, agent);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The agent with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = agentService.delete(id);
        if ("Agent deleted successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
