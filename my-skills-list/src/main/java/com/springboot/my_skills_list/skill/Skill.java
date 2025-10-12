package com.springboot.my_skills_list.skill;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

/**
 * The Skill entity represents a single skill record in the database.
 * It is managed by JPA (via Hibernate) and maps directly to a table named "Skill".
 * Each instance corresponds to one row in the database.
 */
@Entity
public class Skill {
    
    // ================================
    // 🔹 Fields / Table Columns
    // ================================

    /** 
     * Primary key for the Skill entity.
     * The @GeneratedValue annotation lets JPA automatically assign an ID value.
     */
    @Id
    @GeneratedValue
    private int id;

    /** 
     * The username associated with this skill (e.g., owner or creator).
     */
    private String username;

    /**
     * Description of the skill.
     * Must be at least 5 characters long, validated by @Size.
     */
    @Size(min = 5, message = "Enter at least 5 characters")
    private String description;

    /**
     * The target date for achieving or improving this skill.
     * Uses LocalDate for date-only (no time zone) storage.
     */
    private LocalDate targetDate;

    /**
     * Indicates whether the skill goal is completed (true) or still pending (false).
     */
    private boolean done;

    // ================================
    // 🔹 Constructors
    // ================================

    /** 
     * Default constructor (required by JPA). 
     * JPA uses reflection to instantiate entities.
     */
    public Skill() {
    }

    /**
     * Parameterized constructor for easy instantiation.
     * Often used in tests or service layers.
     */
    public Skill(int id, String username, String description, LocalDate targetDate, boolean done) {
        super();
        this.id = id;
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.done = done;
    }

    // ================================
    // 🔹 Getters and Setters
    // Provide controlled access to fields.
    // Required for JPA to map entity properties.
    // ================================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    // ================================
    // 🔹 toString() Override
    // Used for logging and debugging purposes.
    // Provides a readable representation of the entity.
    // ================================
    @Override
    public String toString() {
        return "Skill [id=" + id + 
               ", username=" + username + 
               ", description=" + description + 
               ", targetDate=" + targetDate + 
               ", done=" + done + "]";
    }
}
