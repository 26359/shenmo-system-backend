package com.shenmo.shenmo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shenmo.shenmo.model.Counter;
import com.shenmo.shenmo.service.CounterService;

@RestController
@RequestMapping("/api/counters")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Counter counter) {
        String message = counterService.save(counter);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Counter>> getAll() {
        return ResponseEntity.ok(counterService.findAll());
    }

    @GetMapping("/free")
    public ResponseEntity<List<Counter>> freeCounters() {
        return ResponseEntity.ok(counterService.findFreeCounters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Counter> counter = counterService.findById(id);
        if (counter.isPresent()) {
            return ResponseEntity.ok(counter.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The counter with that id does not exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Counter counter) {
        Optional<Counter> updated = counterService.update(id, counter);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The counter with that id does not exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        String message = counterService.delete(id);
        if ("Counter deleted successfully".equals(message)) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
