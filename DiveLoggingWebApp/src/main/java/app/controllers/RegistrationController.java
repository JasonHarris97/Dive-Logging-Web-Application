package app.controllers;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import app.models.User;
import app.services.EmailService;
import app.services.UserService;

@Controller
public class RegistrationController {
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserService userService;
	private EmailService emailService;
	
	@Autowired
	public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, EmailService emailService) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.emailService = emailService;
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
			// New user. Create user and send confimation email
			
			// Dicable user until they click on confirmation link
			user.setEnabled(false);
			
			// Generate random 36-charaacter string token for confimation link
			user.setConfirmationToken(UUID.randomUUID().toString());
			
			userService.saveUser(user);
			
			String appUrl = request.getScheme() + "://"+ request.getServerName();
			
			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			
			registrationEmail.setTo(user.getEmail());
			registrationEmail.setSubject("Registration Confirmation");
			registrationEmail.setText("To confirm your email address, please click the link below:\n"
					+ appUrl + ":8080/confirm?token=" + user.getConfirmationToken());
			registrationEmail.setFrom("noreply@domain.com");
			
			emailService.sendEmail(registrationEmail);
			
			modelAndView.addObject("confirmationMessage", "A confirmation email has be sent to " + user.getEmail());
			modelAndView.setViewName("registration");
		}
		
		return modelAndView;
	}
	
	// Process confirmation link
	@RequestMapping(value="/confirm", method = RequestMethod.GET)
	public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {
			
		User user = userService.findByConfirmationToken(token);
			
		if (user == null) { // No token found in DB
			modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			modelAndView.addObject("confirmationToken", user.getConfirmationToken());
		}
			
		modelAndView.setViewName("confirm");
		return modelAndView;		
	}
	
	// Process confirmation link
	@RequestMapping(value="/confirm", method = RequestMethod.POST)
	public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
				
		modelAndView.setViewName("confirm");
		
		Zxcvbn passwordCheck = new Zxcvbn();
		
		Strength strength = passwordCheck.measure(requestParams.get("password"));
		
		if (strength.getScore() < 3) {
			bindingResult.reject("password");
			
			redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

			modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
			System.out.println(requestParams.get("token"));
			return modelAndView;
		}
	
		// Find the user associated with the reset token
		User user = userService.findByConfirmationToken(requestParams.get("token"));

		// Set new password
		user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

		// Set user to enabled
		user.setEnabled(true);
		
		// Save user
		userService.saveUser(user);
		
		modelAndView.addObject("successMessage", "Your password has been set!");
		return modelAndView;		
	}
	
}
