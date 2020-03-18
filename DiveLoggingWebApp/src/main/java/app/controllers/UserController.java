package app.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.config.InitialDataLoader;
import app.models.Dive;
import app.models.User;
import app.services.DiveService;
import app.services.UserService;
import app.web.UserDto;

@Controller
@RequestMapping(value = "/user", produces = {MediaType.TEXT_HTML_VALUE})
public class UserController{
	
	private final static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private DiveService diveService;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
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
            return "user/registration";
        }

        userService.save(userDto);
        return "redirect:/user/uploadUserImages/" + userService.findByUsername(userDto.getUsername()).getId();
    }
    
    @RequestMapping(value = "/uploadUserImages/{userId}", method = RequestMethod.GET)
	public String showUploadUserImages(@PathVariable("userId") String userId, Model model) {
    	User user = userService.findById(Long.parseLong(userId));
		model.addAttribute("newUser", user);
		
		return "user/uploadUserImages";
	}
    
    @RequestMapping(value="/view/{identification}", method = RequestMethod.GET)
	public String getUserProfilePage(@PathVariable("identification") String identification, Model model) {
		User user = userService.findByUsername(identification);
		if(user == null) {
			try {
				user = userService.findById(Integer.parseInt(identification));
			} catch (Exception e){
				log.info("Not an integer");
			}
		}
		model.addAttribute("returnedDives", diveService.findAllByDiveOwner(user));
		model.addAttribute("returnedUser", user);
		
		return "user/view";
	}
}

