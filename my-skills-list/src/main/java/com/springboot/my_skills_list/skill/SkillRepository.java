package com.springboot.my_skills_list.skill;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface extends Spring Data JPA’s {@link JpaRepository}, which provides:
 *   - Standard CRUD methods (save, findAll, deleteById, etc.)
 *   - Paging and sorting support
 *
 * <Skill, Integer> → Generic types used:
 *   - Skill: the entity type this repository manages
 *   - Integer: the type of the entity’s primary key (id field)
 */
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    /**
     * Custom finder method provided by Spring Data JPA.
     *
     * Spring automatically implements this method by interpreting its name:
     *  - "findBy" → tells Spring to generate a query
     *  - "Username" → refers to the 'username' field in the Skill entity
     *
     * Equivalent SQL (automatically generated):
     *   SELECT * FROM skill WHERE username = ?
     *
     * @param username the username used to filter skills
     * @return a list of all Skill objects belonging to the specified username
     */
    public List<Skill> findByUsername(String username);
}

