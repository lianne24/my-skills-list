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
 * SkillControllerJpa - handles all web requests related to the "Skill" entity
 */
@Controller
@SessionAttributes("name") // Keeps the logged-in username in the session for use across multiple requests
public class SkillControllerJpa {

    // Repository for interacting with the database (CRUD via Spring Data JPA)
    private SkillRepository skillRepository;

    /**
     * Constructor injection.
     */
    public SkillControllerJpa(SkillRepository skillRepository) {
        super();
        this.skillRepository = skillRepository;
    }

    
    // List all skills
    @RequestMapping("list-skills")
    public String listAllSkills(ModelMap model) {
        String username = getLoggedInUsername(model);
        List<Skill> skills = skillRepository.findByUsername(username);
        model.addAttribute("skills", skills);
        return "listSkills"; // View name → /WEB-INF/jsp/listSkills.jsp
    }

    // Show the form for adding a new skill
    @RequestMapping(value = "add-skill", method = RequestMethod.GET)
    public String showNewSkillPage(ModelMap model) {
        String username = getLoggedInUsername(model);
        // Create a default Skill object with a future target date
        Skill skill = new Skill(0, username, "", LocalDate.now().plusYears(1), false);
        model.put("skill", skill);
        return "skill"; // View name → /WEB-INF/jsp/skill.jsp
    }

    
    // Handle form submission for adding a new skill
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

    // Remove a skill by ID
    @RequestMapping("delete-skill")
    public String deleteSkill(@RequestParam int id) {
        skillRepository.deleteById(id);
        return "redirect:list-skills";
    }

    // Show form pre-filled with existing skill details
    @RequestMapping(value = "update-skill", method = RequestMethod.GET)
    public String showUpdateSkillPage(@RequestParam int id, ModelMap model) {
        Skill skill = skillRepository.findById(id).get();
        model.addAttribute("skill", skill);
        return "skill";
    }

    // Handle submission of the updated skill form
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

    // Get logged-in user’s name from Spring Security
    private String getLoggedInUsername(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
