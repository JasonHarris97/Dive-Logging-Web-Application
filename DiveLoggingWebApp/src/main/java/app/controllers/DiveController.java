package app.controllers;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import app.models.Dive;
import app.models.User;
import app.services.DiveService;
import app.services.UserService;
import app.web.QueryDto;
import app.web.UserDto;

@Controller
@RequestMapping(value = "/dive", produces = {MediaType.TEXT_HTML_VALUE})
public class DiveController {
	
	@Autowired
	private DiveService diveService;
	
	@Autowired
	private UserService userService;
	
    @ModelAttribute("dive")
    public Dive dive() {
        return new Dive();
    }
    
    @ModelAttribute("query")
    public QueryDto queryDto() {
        return new QueryDto();
    }
	
	@RequestMapping(value = {"/query", "/"}, method = RequestMethod.GET)
	public String getAllDives(HttpServletRequest request, Model model) {
		int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10
        
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        
		model.addAttribute("returnedDives", diveService.findAll(PageRequest.of(page, size)));
		return "dive/query";
	}
	
	// CURRENT ---------------------------------------------------------------------------
	@RequestMapping(value = {"/query"}, method = RequestMethod.POST)
	public String performDiveQuery(@ModelAttribute("query") QueryDto query, Model model,
			BindingResult result) {
		
		if (result.hasErrors()) {
			return "dive/query";
		}
		
		if(!query.getInputString().isEmpty()) {
			model = performQuery(query, model);
			return "dive/query";
		} else {
			model.addAttribute("returnedDives", new ArrayList<String>());
			return "dive/query";
		}
	}
	// CURRENT ---------------------------------------------------------------------------
	
	@RequestMapping(value = "/view/{diveId}", method = RequestMethod.GET)
	public String getDiveDetails(@PathVariable("diveId") String diveId, Model model) {
		Dive dive = diveService.findById(Long.parseLong(diveId));
		model.addAttribute("dive", dive);
		
		return "dive/view";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadDiveForm(Model model) {
		return "dive/upload";
	}
	
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String saveDiveToDatabase(@ModelAttribute("dive") Dive dive, Principal principal,
    		BindingResult result) {
    	
		if (result.hasErrors()) {
			return "dive/upload";
		}
		
		User currentUser = userService.findByUsername(principal.getName());
		dive.setDiveOwner(currentUser);
		
		if(dive.getStartTime() != null && dive.getEndTime() != null) {
			dive.setDiveDuration(Duration.between(dive.getStartTime(), dive.getEndTime()));
		} else { 
			dive.setDiveDuration(Duration.ZERO);
		}
		
		
		if(dive.getTankEnd() != 0 && dive.getTankStart() != 0) {
			dive.setTankUsage(dive.getTankStart()-dive.getTankEnd());
		} else {
			dive.setTankUsage(0.0);
		}
		
		currentUser.setNoOfDives(currentUser.getNoOfDives()+1);
		dive.setDiveNo(currentUser.getNoOfDives());
        diveService.save(dive);
        
        return "redirect:/dive/uploadImages/" + dive.getId();
    }
    
    @RequestMapping(value = "/map", method = RequestMethod.GET)
	public String getWorldMap(Model model) {
		model.addAttribute("dives", diveService.findFifty());
		return "dive/map";
	}
    
    @RequestMapping(value = "/uploadImages/{diveId}", method = RequestMethod.GET)
	public String showUploadDiveImages(@PathVariable("diveId") String diveId, Model model) {
    	Dive dive = diveService.findById(Long.parseLong(diveId));
		model.addAttribute("dive", dive);
		
		return "dive/uploadImages";
	}
    
    private Model performQuery(QueryDto query, Model model) {

    	if(query.getSearchOption().equals("country")) {
			model.addAttribute("returnedDives", diveService.findAllByCountry(query.getInputString(), Sort.by(Sort.Direction.ASC, query.getOrderBy())));
		} else if (query.getSearchOption().equals("username")){
			User diveOwner = userService.findByUsername(query.getInputString());
			model.addAttribute("returnedDives", diveService.findAllByDiveOwner(diveOwner, Sort.by(Sort.Direction.ASC, query.getOrderBy())));
		} else if (query.getSearchOption().equals("date")){
			model.addAttribute("returnedDives", diveService.findAll(Sort.by(Sort.Direction.ASC, query.getOrderBy())));
		} else if (query.getSearchOption().equals("location")){
			model.addAttribute("returnedDives", diveService.findAllByLocation(query.getInputString(), Sort.by(Sort.Direction.ASC, query.getOrderBy())));
		} else if (query.getSearchOption().equals("padiLevel")) {
			model.addAttribute("returnedDives", diveService.findAllByDiveOwnerPadiLevel(query.getInputString(), Sort.by(Sort.Direction.ASC, query.getOrderBy())));
		} else {
			model.addAttribute("returnedDives", diveService.findAll());
		}
    	return model;
    }


}
