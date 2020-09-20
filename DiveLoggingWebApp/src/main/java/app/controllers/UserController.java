package app.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.models.DBFile;
import app.models.User;
import app.services.DBFileService;
import app.services.DiveService;
import app.services.UserService;
import app.strings.StringLists;
import app.web.QueryDto;
import app.web.UserDto;

@Controller
@RequestMapping(value = "/user", produces = {MediaType.TEXT_HTML_VALUE})
public class UserController{
	
	private final static Logger log = LoggerFactory.getLogger(UserController.class);
	private StringLists stringLists = new StringLists();
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private DiveService diveService;
	
	@Autowired
    private DBFileService dbFileService;

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
    	 model.addAttribute("countries", stringLists.getCountries());
         model.addAttribute("padiLevels",stringLists.getPadiLevels());
        return "user/registration";
    }
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
        BindingResult result, HttpServletRequest request, Model model) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        
        existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        
        if (result.hasErrors()) {
       	 	model.addAttribute("countries", stringLists.getCountries());
       	 	model.addAttribute("padiLevels",stringLists.getPadiLevels());
            return "user/registration";
        }

        User returnedUser = userService.save(userDto);
        
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
		model.addAttribute("mostRecentDive", diveService.findMostRecentByUser(user));
		model.addAttribute("returnedUser", user);
		
		return "user/view";
	}
    
    @RequestMapping(value = {"/find", "/"}, method = RequestMethod.GET)
	public String showFindUserPage(Model model) {
		int page = 0; //default page number is 0 (yes it is weird)
	    int size = 12; //default page size is 12
	    
	    QueryDto query = new QueryDto();
	    query.setInputString("");
	    query.setSearchOption("all");
	    query.setOrderBy("noOfDives");
	    query.setSortBy("descending");
	    query.setPageNo("1");
	    query.setSource("query");
	    query.setPageSize("12");
	    
	    model.addAttribute("query", query);
		model.addAttribute("returnedUsers", userService.findAll(PageRequest.of(page, size)));
		model.addAttribute("countries", stringLists.getCountries());
	    model.addAttribute("padiLevels",stringLists.getPadiLevels());
		
		return "user/find";
	}
	
	@RequestMapping(value = {"/find"}, method = RequestMethod.POST)
	public String performUserQuery(@ModelAttribute("query") QueryDto query, Model model,
			BindingResult result) {
		
		int page = 0; //default page number is 0 (yes it is weird)
        int size = 12; //default page size is 12
        
        if (query.getPageNo() != null) {
            page = Integer.parseInt(query.getPageNo()) - 1;
        }
        
        if (query.getPageSize() != null) {
            size = Integer.parseInt(query.getPageSize());
        }
		
		if (result.hasErrors()) {
			return "user/"+query.getSource();
		}
		
		if(query.getSearchOption().equals("all") || query.getSearchOption().equals("country") 
				|| query.getSearchOption().equals("padiLevel")) {
			model = performQueryPageable(query, model, page, size);
		} else if(!query.getInputString().isEmpty()) {
			model = performQueryPageable(query, model, page, size);
		} else {
			model.addAttribute("returnedUsers", new ArrayList<String>());
		}
		
		model.addAttribute("countries", stringLists.getCountries());
	    model.addAttribute("padiLevels",stringLists.getPadiLevels());
		model.addAttribute("query", query);
		
		return "user/"+query.getSource();
	}
	  
	private Model performQueryPageable(QueryDto query, Model model, int page, int size) {
    	String orderBy = query.getOrderBy();
    	Sort.Direction sortBy;
    	
    	if(query.getSortBy().equals("ascending")){
    		sortBy = Sort.Direction.ASC;
    	} else {
    		sortBy = Sort.Direction.DESC;
    	}
    	
    	if(orderBy == null) {
    		orderBy = "username";
    	}
    	
    	if(query.getSearchOption().equals("country")) { 
    		// country
			model.addAttribute("returnedUsers", userService.findByCountry( query.getCountry(), PageRequest.of(page, size, Sort.by(sortBy, orderBy)) ));
		} else if (query.getSearchOption().equals("username")){
			// username
			model.addAttribute("returnedUsers", userService.findByUsernameContaining(query.getInputString(), PageRequest.of(page, size, Sort.by(sortBy, orderBy))));
		} else if (query.getSearchOption().equals("name")){
			// name
			model.addAttribute("returnedUsers", userService.findByName(query.getInputString(), PageRequest.of(page, size, Sort.by(sortBy, orderBy))));
		} else if (query.getSearchOption().equals("padiLevel")) {
			// padiLevel
			model.addAttribute("returnedUsers", userService.findByPadiLevel(query.getPadiLevel(), PageRequest.of(page, size, Sort.by(sortBy, orderBy))));
		} else if (query.getSearchOption().equals("padiNo")){
			// padiNo
			model.addAttribute("returnedUsers", userService.findByPadiNo(query.getInputString(), PageRequest.of(page, size)));
		} else if (query.getSearchOption().equals("all")){
			// all
			model.addAttribute("returnedUsers", userService.findAll(PageRequest.of(page, size, Sort.by(sortBy, orderBy))));
		} else {
			model.addAttribute("returnedUsers", userService.findAll(PageRequest.of(page, size, Sort.by(sortBy, orderBy))));
		}
    	return model;
    }
}

