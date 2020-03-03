package app.controllers;

import java.security.Principal;
import java.time.Duration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAllDives(Model model) {
		model.addAttribute("dives", diveService.findAll());
		return "dive/index";
	}
	
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
		
		dive.setDiveDuration(Duration.between(dive.getStartTime(), dive.getEndTime()));
		
        diveService.save(dive);
        return "redirect:/dive";
    }


}
