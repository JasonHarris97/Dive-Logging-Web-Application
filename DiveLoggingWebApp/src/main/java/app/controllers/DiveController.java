package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.models.Dive;
import app.services.DiveService;

@Controller
@RequestMapping(value = "/dive", produces = {MediaType.TEXT_HTML_VALUE})
public class DiveController {
	
	@Autowired
	private DiveService diveService;
	
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

}
