package app.controllers;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.models.User;
import app.services.DiveService;
import app.services.UserService;
import app.web.QueryDto;
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
    
    @ModelAttribute("query")
    public QueryDto queryDto() {
        return new QueryDto();
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
        BindingResult result, HttpServletRequest request) {

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
        
        try {
            request.login(userDto.getUsername(), userDto.getPassword());
        } catch (ServletException e) {
            log.error("Error while login ", e);
        }
        
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
    
    @RequestMapping(value = {"/find", "/"}, method = RequestMethod.GET)
	public String showFindUserPage(Model model) {
		model.addAttribute("returnedUsers", userService.findAll());
		return "user/find";
	}
	
	// CURRENT ---------------------------------------------------------------------------
	@RequestMapping(value = {"/find"}, method = RequestMethod.POST)
	public String performUserQuery(@ModelAttribute("query") QueryDto query, Model model,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "user/"+query.getSource();
		}
		
		if(query.getSearchOption().equals("all")) {
			model = performQuery(query, model);
			return "user/"+query.getSource();
		} else if(!query.getInputString().isEmpty()) {
			model = performQuery(query, model);
			return "user/"+query.getSource();
		} else {
			model.addAttribute("returnedUsers", new ArrayList<String>());
			return "user/"+query.getSource();
		}
	}
	
	  private Model performQuery(QueryDto query, Model model) {
	    	String orderBy = query.getOrderBy();
	    	if(orderBy == null) {
	    		orderBy = "username";
	    	}
	    	
	    	if(query.getSearchOption().equals("country")) { 
	    		// country
				model.addAttribute("returnedUsers", userService.findAllByCountry(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
			} else if (query.getSearchOption().equals("username")){
				// username
				model.addAttribute("returnedUsers", userService.findByUsername(query.getInputString()));
			} else if (query.getSearchOption().equals("name")){
				// name
				model.addAttribute("returnedUsers", userService.findAllByName(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
			} else if (query.getSearchOption().equals("padiLevel")) {
				// padiLevel
				model.addAttribute("returnedUsers", userService.findAllByPadiLevel(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
			} else if (query.getSearchOption().equals("padiNo")){
				// padiNo
				model.addAttribute("returnedUsers", userService.findByPadiNo(query.getInputString()));
			} else if (query.getSearchOption().equals("all")){
				// all
				model.addAttribute("returnedUsers", userService.findAll(Sort.by(Sort.Direction.ASC, orderBy)));
			} else {
				model.addAttribute("returnedUsers", userService.findAll(Sort.by(Sort.Direction.ASC, orderBy)));
			}
	    	return model;
	    }
}

