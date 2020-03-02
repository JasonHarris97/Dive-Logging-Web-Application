package app.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import app.controllers.RegistrationController;
import app.models.Dive;
import app.models.Role;
import app.models.User;
import app.services.DiveService;
import app.services.UserService;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiveService diveService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(userService.count() > 0) {
			log.info("Database (events) already populated. Skipping data initialization.");
			return;
		}

		// Test User
		User testUser = new User();
		testUser.setEmail("test@gmail.com");
		testUser.setFirstName("John");
		testUser.setLastName("Smith");
		testUser.setPassword(passwordEncoder.encode("test"));
		testUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userService.save(testUser);
		log.info("Test User Added");	
		
		// Test Dive Log
		Dive testDive = new Dive(testUser, "test descrption");
		diveService.save(testDive);
		log.info("Test Dive Added with ID: " + testDive.getId());

	}
}
