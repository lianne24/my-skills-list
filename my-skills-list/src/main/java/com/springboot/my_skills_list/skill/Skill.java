package com.springboot.my_skills_list.skill;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

/**
 * The Skill entity represents a single skill record in the database.
 */

@Entity
public class Skill {
    
    // Fields / Table Columns

    // Primary key for the Skill entity.
    @Id
    @GeneratedValue
    private int id;

    // The username associated with this skill.
    private String username;

    // Description of the skill.
    @Size(min = 5, message = "Enter at least 5 characters")
    private String description;

    // The target date for achieving or improving this skill.
    private LocalDate targetDate;

    // Indicates whether the skill goal is completed (true) or still pending (false).
    private boolean done;

    
    // Constructors

    // Default constructor (required by JPA). 
    public Skill() {
    }

    // Parameterized constructor for easy instantiation.
    public Skill(int id, String username, String description, LocalDate targetDate, boolean done) {
        super();
        this.id = id;
        this.username = username;
        this.description = description;
        this.targetDate = targetDate;
        this.done = done;
    }

    
    // Getters and Setters

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

	@Override
	public String toString() {
		return "Skill [id=" + id + ", username=" + username + ", description=" + description
				+ ", targetDate=" + targetDate + ", done=" + done + "]";
	}


}
