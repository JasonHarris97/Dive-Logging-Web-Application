package app.controllers;

import java.security.Principal;
import java.time.Duration;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import app.config.InitialDataLoader;
import app.models.Dive;
import app.models.User;
import app.services.DiveService;
import app.services.UserService;
import app.web.QueryDto;

@Controller
@RequestMapping(value = "/dive", produces = {MediaType.TEXT_HTML_VALUE})
public class DiveController {
	
	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);
	
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
	public String showQueryDivesPage(HttpServletRequest request, Model model) {
		int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10
        
        QueryDto query = new QueryDto();
        query.setSearchOption("all");
        query.setInputString("");
        query.setOrderBy("date");
        query.setPageNo("1");
        query.setSource("query");
        
        model.addAttribute("query", query);
        model.addAttribute("returnedDives", diveService.findAll(PageRequest.of(page, size)));
        log.info("Querying all dives - Page: " + page + " Size: " + size);
         
		return "dive/query";
	}
	
	// CURRENT ---------------------------------------------------------------------------
	@RequestMapping(value = {"/query"}, method = RequestMethod.POST)
	public String performDiveQuery(@ModelAttribute("query") QueryDto query, Model model,
			HttpServletRequest request, BindingResult result) {
		
		int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10
        
        if (query.getPageNo() != null) {
        	log.info("/n PageNo:" + query.getPageNo() + "/n");
            page = Integer.parseInt(query.getPageNo()) - 1;
        }
		
		if (result.hasErrors()) {
			return "dive/"+query.getSource();
		}
		
		if(query.getSource().equals("query")) {
			if(query.getSearchOption().equals("all")) {
				model = performQueryPageable(query, model, page, 10);
			} else if(!query.getInputString().isEmpty()) {
				log.debug("Querying:" + query.getInputString() + " Order By: " + query.getOrderBy() + " Page No: " + page);
				model = performQueryPageable(query, model, page, 10);
			} else {
				model.addAttribute("returnedDives", new ArrayList<String>());
			}
		} else {
			if(query.getSearchOption().equals("all")) {
				model = performQuery(query, model);
			} else if(!query.getInputString().isEmpty()) {
				model = performQuery(query, model);
			} else {
				model.addAttribute("returnedDives", new ArrayList<String>());
			}
		}
		
		model.addAttribute("query", query);
		
		return "dive/"+query.getSource();
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
		model.addAttribute("returnedDives", diveService.findFifty());
		return "dive/map";
	}
    
    @RequestMapping(value = "/uploadImages/{diveId}", method = RequestMethod.GET)
	public String showUploadDiveImages(@PathVariable("diveId") String diveId, Model model) {
    	Dive dive = diveService.findById(Long.parseLong(diveId));
		model.addAttribute("dive", dive);
		
		return "dive/uploadImages";
	}
    
    private Model performQuery(QueryDto query, Model model) {
    	String orderBy = query.getOrderBy();
    	if(orderBy == null) {
    		orderBy = "diveOwnerUsername";
    	}
    	
    	if(query.getSearchOption().equals("country")) {
			model.addAttribute("returnedDives", diveService.findAllByCountry(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
		} else if (query.getSearchOption().equals("username")){
			model.addAttribute("returnedDives", diveService.findAllByDiveOwner(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
		} else if (query.getSearchOption().equals("date")){
			model.addAttribute("returnedDives", diveService.findAll(Sort.by(Sort.Direction.ASC, orderBy)));
		} else if (query.getSearchOption().equals("location")){
			model.addAttribute("returnedDives", diveService.findAllByLocation(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
		} else if (query.getSearchOption().equals("padiLevel")) {
			model.addAttribute("returnedDives", diveService.findAllByDiveOwnerPadiLevel(query.getInputString(), Sort.by(Sort.Direction.ASC, orderBy)));
		} else if (query.getSearchOption().equals("all")){
			model.addAttribute("returnedDives", diveService.findAll(Sort.by(Sort.Direction.ASC, orderBy)));
		} else {
			model.addAttribute("returnedDives", diveService.findAll(Sort.by(Sort.Direction.ASC, orderBy)));
		}
    	return model;
    }
    
    private Model performQueryPageable(QueryDto query, Model model, int page, int size) {
    	String orderBy = query.getOrderBy();
    	if(orderBy == null) {
    		orderBy = "diveOwnerUsername";
    	}
    	
    	if(query.getSearchOption().equals("country")) {
			model.addAttribute("returnedDives", diveService.findAllByCountry(query.getInputString(), PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy))));
		} else if (query.getSearchOption().equals("username")){
			Page<Dive> returnedDives = diveService.findAllByDiveOwnerUsername(query.getInputString(), PageRequest.of(page, size, Sort.by(orderBy).ascending()));
			model.addAttribute("returnedDives", returnedDives);
		} else if (query.getSearchOption().equals("date")){
			model.addAttribute("returnedDives", diveService.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy))));
		} else if (query.getSearchOption().equals("location")){
			model.addAttribute("returnedDives", diveService.findAllByLocation(query.getInputString(), PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy))));
		} else if (query.getSearchOption().equals("padiLevel")) {
			model.addAttribute("returnedDives", diveService.findAllByDiveOwnerPadiLevel(query.getInputString(), PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy))));
		} else if (query.getSearchOption().equals("all")){
			Page<Dive> returnedDives = diveService.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy)));
			model.addAttribute("returnedDives", returnedDives);
		} else {
			model.addAttribute("returnedDives", diveService.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy))));
		}
    
    	return model;
    }


}
