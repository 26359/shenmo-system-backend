package com.shenmo.shenmo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shenmo.shenmo.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {

    @Query("SELECT s FROM Skill s WHERE s.agents IS EMPTY")
    List<Skill> findUnusedSkills();
}
