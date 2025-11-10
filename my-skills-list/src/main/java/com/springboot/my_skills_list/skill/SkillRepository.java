package com.springboot.my_skills_list.skill;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// This SkillRepository interface extends Spring Data JPA’s
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    //Custom findByUsername method provided by Spring Data JPA.
    public List<Skill> findByUsername(String username);
}

