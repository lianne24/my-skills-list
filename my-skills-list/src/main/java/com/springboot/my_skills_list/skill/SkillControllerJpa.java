package com.springboot.my_skills_list.skill;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import jakarta.validation.Valid;

/**
 * SkillControllerJpa
 * ------------------
 * This controller handles all web requests related to the "Skill" entity
 *
 * It provides CRUD functionality (Create, Read, Update, Delete)
 * using Spring MVC for web interaction and Spring Data JPA for persistence.
 *
 * Each handler method is mapped to a specific URL, returning either a view name
 * (e.g., "listSkills.jsp") or redirecting to another route.
 */
@Controller
@SessionAttributes("name") // Keeps the logged-in username in the session for use across multiple requests
public class SkillControllerJpa {

    // Repository for interacting with the database (CRUD via Spring Data JPA)
    private SkillRepository skillRepository;

    /**
     * Constructor injection.
     * Spring automatically injects the SkillRepository dependency.
     */
    public SkillControllerJpa(SkillRepository skillRepository) {
        super();
        this.skillRepository = skillRepository;
    }

    // ===========================================================
    // 🔹 READ: List all skills
    // ===========================================================

    /**
     * Handles GET requests to /list-skills.
     * Fetches all skills belonging to the currently logged-in user.
     * 
     * @param model the ModelMap used to pass data to the view layer
     * @return the view name "listSkills" which displays the list of skills
     */
    @RequestMapping("list-skills")
    public String listAllSkills(ModelMap model) {
        String username = getLoggedInUsername(model);
        List<Skill> skills = skillRepository.findByUsername(username);
        model.addAttribute("skills", skills);
        return "listSkills"; // View name → /WEB-INF/jsp/listSkills.jsp
    }

    // ===========================================================
    // 🔹 CREATE: Show the form for adding a new skill
    // ===========================================================

    /**
     * Handles GET requests to /add-skill.
     * Displays a blank form for adding a new skill.
     * 
     * Uses "2-way binding": the form fields are tied to the Skill bean.
     */
    @RequestMapping(value = "add-skill", method = RequestMethod.GET)
    public String showNewSkillPage(ModelMap model) {
        String username = getLoggedInUsername(model);
        // Create a default Skill object with a future target date
        Skill skill = new Skill(0, username, "", LocalDate.now().plusYears(1), false);
        model.put("skill", skill);
        return "skill"; // View name → /WEB-INF/jsp/skill.jsp
    }

    // ===========================================================
    // 🔹 CREATE: Handle form submission for adding a new skill
    // ===========================================================

    /**
     * Handles POST requests to /add-skill.
     * Validates input and saves a new skill to the database.
     * 
     * @param skill   the Skill object bound to the form input
     * @param result holds validation errors, if any
     * @return a redirect to /list-skills upon success, or redisplay the form on validation error
     */
    @RequestMapping(value = "add-skill", method = RequestMethod.POST)
    public String addNewSkill(ModelMap model, @Valid Skill skill, BindingResult result) {

        // If validation fails, redisplay the form
        if (result.hasErrors()) {
            return "skill";
        }

        // Associate skill with logged-in user
        String username = getLoggedInUsername(model);
        skill.setUsername(username);

        // Persist the new skill
        skillRepository.save(skill);

        // Redirect prevents form resubmission if the user refreshes the page
        return "redirect:list-skills";
    }

    // ===========================================================
    // 🔹 DELETE: Remove a skill by ID
    // ===========================================================

    /**
     * Handles requests to /delete-skill.
     * Deletes a skill by its ID.
     *
     * @param id the ID of the skill to delete
     * @return redirect to /list-skills after deletion
     */
    @RequestMapping("delete-skill")
    public String deleteSkill(@RequestParam int id) {
        skillRepository.deleteById(id);
        return "redirect:list-skills";
    }

    // ===========================================================
    // 🔹 UPDATE: Show form pre-filled with existing skill details
    // ===========================================================

    /**
     * Handles GET requests to /update-skill.
     * Retrieves an existing skill by ID and displays it for editing.
     *
     * @param id    the ID of the skill to update
     * @param model model to pass the skill object to the view
     * @return the "skill" view pre-populated with existing skill data
     */
    @RequestMapping(value = "update-skill", method = RequestMethod.GET)
    public String showUpdateSkillPage(@RequestParam int id, ModelMap model) {
        Skill skill = skillRepository.findById(id).get();
        model.addAttribute("skill", skill);
        return "skill";
    }

    // ===========================================================
    // 🔹 UPDATE: Handle submission of the updated skill form
    // ===========================================================

    /**
     * Handles POST requests to /update-skill.
     * Validates and saves the updated skill back to the database.
     *
     * @param skill   the Skill object with updated data
     * @param result validation result object
     * @return redirect to /list-skills if successful, or redisplay form if validation fails
     */
    @RequestMapping(value = "update-skill", method = RequestMethod.POST)
    public String updateSkill(ModelMap model, @Valid Skill skill, BindingResult result) {

        if (result.hasErrors()) {
            return "skill";
        }

        String username = getLoggedInUsername(model);
        skill.setUsername(username);
        skillRepository.save(skill);
        return "redirect:list-skills";
    }

    // ===========================================================
    // 🔹 Utility: Get logged-in user’s name from Spring Security
    // ===========================================================

    /**
     * Retrieves the username of the currently authenticated user
     * from the Spring Security context.
     *
     * @param model not directly used here, but kept for consistency
     * @return the username (String) of the logged-in user
     */
    private String getLoggedInUsername(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
