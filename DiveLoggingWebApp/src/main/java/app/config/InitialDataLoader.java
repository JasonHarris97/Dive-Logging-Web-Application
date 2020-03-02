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
import app.models.Role;
import app.models.User;
import app.services.UserService;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		if(userService.count() > 0) {
			log.info("Database (events) already populated. Skipping data initialization.");
			return;
		}

		User testUser = new User();
		testUser.setEmail("test@gmail.com");
		testUser.setFirstName("John");
		testUser.setLastName("Smith");
		testUser.setPassword(passwordEncoder.encode("test"));
		testUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userService.save(testUser);
		log.info("JOHN SMITH ADDED");
		log.info("JOHN SMITH ADDED");
		log.info("JOHN SMITH ADDED");
	}
}
