package com.shenmo.shenmo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenmo.shenmo.model.Skill;
import com.shenmo.shenmo.repository.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public String save(Skill skill) {
        skillRepository.save(skill);
        return "Skill saved successfully";
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> findById(UUID id) {
        return skillRepository.findById(id);
    }

    public Optional<Skill> update(UUID id, Skill skill) {
        Optional<Skill> existing = skillRepository.findById(id);
        if (existing.isPresent()) {
            Skill s = existing.get();
            s.setName(skill.getName());
            skillRepository.save(s);
            return Optional.of(s);
        }
        return Optional.empty();
    }

    public String delete(UUID id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
            return "Skill deleted successfully";
        }
        return "The skill with that id does not exist";
    }
}
