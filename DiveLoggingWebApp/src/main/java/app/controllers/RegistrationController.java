package app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.models.User;
import app.services.UserService;

@Controller
public class RegistrationController {
	
	private UserService userService;
	
	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}
	
	// Registration form 
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		
		return modelAndView;
	}
	
	// Process form submission
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
		
		// See if user already exists in the database
		User userExists = userService.findByEmail(user.getEmail());
		
		System.out.println(userExists);
		
		if(userExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage", "User exists with this email. Please use another email address");
			modelAndView.setViewName("registration");
			bindingResult.reject("email");
		}
		
		if(bindingResult.hasErrors()){
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.setViewName("registration");
		}
		
		return modelAndView;
	}
	
}
