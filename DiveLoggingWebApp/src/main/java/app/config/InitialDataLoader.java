package app.config;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
		testUser.setFirstName("John");
		testUser.setLastName("Smith");
		testUser.setUsername("testUsername");
		testUser.setEmail("test@gmail.com");
		testUser.setPassword(passwordEncoder.encode("test"));
		testUser.setContactNumber("07801418898");
		testUser.setCountry("England");
		testUser.setPadiLevel("Expert");
		testUser.setNoOfDives(0);
		testUser.setNoOfCountries(0);
		testUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userService.save(testUser);
		log.info("Test User Added with ID:" + testUser.getId());	
		
		// Test Dive Log
		Dive testDive = new Dive(testUser, "test descrption");
		testDive.setDate(LocalDate.now());
		testDive.setStartTime(LocalTime.now());
		testDive.setEndTime(LocalTime.now());
		testDive.setDiveDuration(Duration.between(LocalTime.now(), LocalTime.now()));
		testDive.setLatitude(20.0);
		testDive.setLongitude(24.0);
		testDive.setCountry("England");
		testDive.setLocation("Brazil");
		testDive.setDetailedLocation("Brazil, Sao Paulo");
		testDive.setWeather("Rainy");
		testDive.setVisibility("Poor");
		testDive.setWaterType("Salt");
		testDive.setDiveSuit("Standard Wet Suit");
		testDive.setEntry("Boat");
		testDive.setAirTemperature(30.4);
		testDive.setWaterTemperature(24.5);
		testDive.setTankType("Big Tank");
		testDive.setGasType("Oxygen");
		testDive.setTankSize(5000);
		testDive.setTankStart(4789.93);
		testDive.setTankEnd(1340.32);
		testDive.setTankUsage(4789.93-1340.32);
		testDive.setNoOfParticipants(3);
		testDive.setParticipantsList("Jason Harris, Tim Johnson, John Smith");
		testDive.setMaxDepth(345.67);
		testDive.setAltitude(346);
		testDive.setDescription("Test description");
		testDive.setFaunaList("animal1 animal2");
		testDive.setFloraList("plant1 plant2 plant3");
		testDive.setObservationsList("observation1 observation2");
		
		diveService.save(testDive);
		log.info("Test Dive Added with ID: " + testDive.getId());

	}
}
