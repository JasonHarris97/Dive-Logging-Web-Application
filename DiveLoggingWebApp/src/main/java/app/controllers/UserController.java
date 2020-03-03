package app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.models.User;
import app.services.UserService;
import app.web.UserDto;

@Controller
@RequestMapping(value = "/user", produces = {MediaType.TEXT_HTML_VALUE})
public class UserController{
	
	@Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }
    
    @ModelAttribute("activeUser")
    public User user() {
        return new User();
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "user/login";
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        return "user/registration";
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
        BindingResult result) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        
        existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        
        if (result.hasErrors()) {
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/user/login?regSuccess";
    }
    
    @RequestMapping(value="/view/{username}", method = RequestMethod.GET)
	public String getUserProfilePage(@PathVariable("username") String username, Model model) {
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		
		return "user/view";
	}

}

