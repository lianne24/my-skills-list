package com.springboot.my_skills_list.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

//The WelcomeController class serves as the entry point (Front Controller)

@Controller
@SessionAttributes("name") // Keeps the user's name stored in session for use across views
public class WelcomeController {

    // Handles HTTP GET requests for the root URL ("/").
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToWelcomePage(ModelMap model) {
    	
        // Retrieve the logged-in username from the Spring Security context
        model.put("name", getLoggedinUsername());
        // Return the view name; resolved as /WEB-INF/jsp/welcome.jsp (via view resolver)
        return "welcome";
    }

    // Helper method to extract the username of the currently authenticated user.
    private String getLoggedinUsername() {
    	
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Return the username associated with the authentication
        return authentication.getName();
    }
}
