package app.config;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import app.models.Dive;
import app.models.Role;
import app.models.User;
import app.services.DiveService;
import app.services.UserService;
import app.strings.StringLists;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
	
	private final static Logger log = LoggerFactory.getLogger(InitialDataLoader.class);
	
	private final static Faker faker = new Faker(new Locale("en-GB"));
	
	private final static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
	private final static Random rand = new Random();
	private final static StringLists stringLists = new StringLists();
	
	private final static int noOfTestUsers = 2;
	
	private final static int noOfTestDives = 2;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DiveService diveService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		List<User> testUsers;
		
		if (diveService.count() > 0 && userService.count() > 0) {
			log.info("Database (USERS and DIVES) already populated. Skipping data initialization.");
			return;
		} else {
			loadSpecificTestData();
		}
		
		if(userService.count() > 1) {
			testUsers = (List<User>) userService.findAll();
			log.info("Database (USERS) already populated. Skipping data initialization.");
		} else {
			testUsers = loadTestUsers();
		}
		
		if (diveService.count() > 1) {
			log.info("Database (DIVES) already populated. Skipping data initialization.");
		} else {
			loadTestDives(testUsers);
		}
	}
	
	private void loadTestDives(List<User> testUsers) {
		Dive testDive;
		
		int diveNo;
		
		for(diveNo = 0; diveNo < noOfTestDives; diveNo++) {
			testDive = new Dive();
			
			testDive.setDiveTitle(faker.book().title());
			testDive.setDiveSite(faker.address().streetName());
			testDive.setDivePurpose("Recreational");
			testDive.setDiveOwner(testUsers.get(rand.nextInt(testUsers.size())));
			
			User diveOwner = testDive.getDiveOwner();
			diveOwner.setNoOfDives(diveOwner.getNoOfDives()+1);
			userService.save(diveOwner);
			testDive.getDiveOwner();
			testDive.setDiveNo(testDive.getDiveOwner().getNoOfDives());
			testDive.setDiveBuddy(faker.name().fullName());
			
			testDive.setDate(convertToLocalDate(faker.date().past(2000, TimeUnit.DAYS)));
			
			LocalTime startTime = LocalTime.of(rand.nextInt(23), rand.nextInt(59));
			testDive.setStartTime(startTime);
			 
			LocalTime endTime = LocalTime.of((startTime.getHour()+rand.nextInt(23)) % 24, (startTime.getMinute()+rand.nextInt(59)) % 60);
			testDive.setEndTime(endTime);
			
			testDive.setDiveDuration(Duration.between(startTime, endTime));
			
			Address address = faker.address();
			
			testDive.setLatitude(Double.parseDouble(faker.address().latitude()));
			testDive.setLongitude(Double.parseDouble(faker.address().longitude()));
			
			testDive.setCountry(address.country());
			testDive.setLocation(address.city());
			testDive.setDetailedLocation(address.fullAddress());
			
			testDive.setWeather(faker.weather().description());
			testDive.setVisibility(stringLists.getVisabilityOptions().get(rand.nextInt(stringLists.getVisabilityOptions().size())));
			testDive.setWaterType(stringLists.getWaterTypes().get(rand.nextInt(stringLists.getWaterTypes().size())));
			testDive.setWaterBodyType("Lake");
			testDive.setWaterDensity(faker.number().randomDouble(2, 0, 10));
			testDive.setTideLevel("Low");
			testDive.setDiveSuit("Standard Wet Suit");
			testDive.setEntry("Boat");
			testDive.setAirTemperature(faker.number().randomDouble(2, -200, 100));
			testDive.setWaterTemperature(faker.number().randomDouble(2, -200, 100));
			testDive.setTankType("Big Tank");
			testDive.setGasType("Oxygen");
			testDive.setGasMix("Oxygen, Nitrogen, Helium");
			testDive.setFractionOfHelium(faker.number().randomDouble(2, 0, 1));
			testDive.setFractionOfNitrogen(faker.number().randomDouble(2, 0, 1));
			testDive.setTankSize(rand.nextInt(10)*1000);
			testDive.setTankStart(faker.number().randomDouble(2, 0, testDive.getTankSize()));
			testDive.setTankEnd(faker.number().randomDouble(2, 0, (int) testDive.getTankStart()));
			testDive.setTankUsage(testDive.getTankStart()-testDive.getTankEnd());
			testDive.setWeightCarried(faker.number().randomDouble(2, 0, 200));
			testDive.setNoOfParticipants(rand.nextInt(9)+1);
			
			String participantsList = testDive.getDiveOwner().getFirstName() + " " + testDive.getDiveOwner().getLastName();
			for(int i = 0; i < testDive.getNoOfParticipants()-1; i++) {
				participantsList = participantsList + " " + faker.name().firstName() + " " + faker.name().lastName();
			}
			testDive.setParticipantsList(participantsList);
			testDive.setEquipment("Wet Suit, Tank, Camera");
			testDive.setBuoyancy("Good");
			
			testDive.setBottomTime(faker.number().randomDouble(2, 0, 100));;
			testDive.setMaxDepth(faker.number().randomDouble(2, 0, 20000));
			testDive.setAltitude(faker.number().randomDouble(2, 0, 20000));
			
			testDive.setStartCode("startcode");
			testDive.setSurfacingCode("surfacingcode");
			
			testDive.setDescription("Test description");
			testDive.setFaunaList("animal1 animal2");
			testDive.setFloraList("plant1 plant2 plant3");
			testDive.setObservationsList("observation1 observation2");
			
			diveService.save(testDive);
		}	
	}
	
	private List<User> loadTestUsers() {	
		
		List<String> padiLevels = stringLists.getPadiLevels();
		
		User testUser;
		List<User> testUsers = new ArrayList<User>();
		
		for(int i = 0; i < noOfTestUsers; i++) {
			testUser = new User();
			testUser.setFirstName(faker.name().firstName());
			testUser.setLastName(faker.name().lastName());
			testUser.setUsername(testUser.getFirstName() + " " + testUser.getLastName());
			testUser.setFullName(testUser.getFirstName()+" "+testUser.getLastName());
			testUser.setEmail(testUser.getUsername() + "." + fakeValuesService.bothify("????##@gmail.com"));
			testUser.setCountry(faker.address().country());
			testUser.setPassword(passwordEncoder.encode("test"));
			testUser.setContactNumber(faker.phoneNumber().phoneNumber());
			testUser.setPadiLevel(padiLevels.get(rand.nextInt(padiLevels.size())));
			testUser.setPadiNo(faker.number().digits(7));
			testUser.setNoOfDives(0);
			testUser.setNoOfCountries(0);
			testUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
			userService.save(testUser);
			testUsers.add(testUser);
		}
		return testUsers;
	}
	
	private void loadSpecificTestData() {
		// Personal Test Account
		User testUser = new User();
		testUser.setFirstName("Jason");
		testUser.setLastName("Harris");
		testUser.setFullName("Jason Harris");
		testUser.setUsername("JasonHarris");
		testUser.setEmail("test@gmail.com");
		testUser.setPassword(passwordEncoder.encode("test"));
		testUser.setContactNumber("07801418898");
		testUser.setCountry("England");
		testUser.setPadiLevel("Discover Scuba Diving");
		testUser.setPadiNo("789237");
		testUser.setNoOfDives(1);
		testUser.setNoOfCountries(0);
		testUser.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userService.save(testUser);
		
		// Test Dive Log
		Dive testDive = new Dive(testUser, "Test Description. Dive Description. Etc.");
		testDive.getDiveOwner().setNoOfDives(testDive.getDiveOwner().getNoOfDives()+1);
		testDive.setDiveNo(testDive.getDiveOwner().getNoOfDives());
		testDive.setDiveTitle(faker.book().title());
		testDive.setDiveSite(faker.address().streetName());
		testDive.setDiveBuddy(faker.name().fullName());
		testDive.setDivePurpose("Recreational");
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
		testDive.setWaterBodyType("River");
		testDive.setDiveSuit("Standard Wet Suit");
		testDive.setWaterDensity(5.6);
		testDive.setTideLevel("High");
		testDive.setEntry("Boat");
		testDive.setAirTemperature(30.4);
		testDive.setWaterTemperature(24.5);
		testDive.setTankType("Big Tank");
		testDive.setGasType("Oxygen");
		testDive.setGasMix("Oxygen, Nitrogen");
		testDive.setFractionOfHelium(0.12);
		testDive.setFractionOfNitrogen(0.01);
		testDive.setTankSize(5000);
		testDive.setTankStart(4789.93);
		testDive.setTankEnd(1340.32);
		testDive.setTankUsage(4789.93-1340.32);
		testDive.setWeightCarried(189.0);
		testDive.setNoOfParticipants(3);
		testDive.setParticipantsList("Jason Harris, Tim Johnson, John Smith");
		testDive.setEquipment("Dive suit, camera, torch");
		testDive.setBuoyancy("Good");
		testDive.setBottomTime(45.2);
		testDive.setStartCode("start code");
		testDive.setSurfacingCode("surfacing code");
		testDive.setMaxDepth(345.67);
		testDive.setAltitude(346);
		testDive.setDescription("Test description");
		testDive.setFaunaList("animal1 animal2");
		testDive.setFloraList("plant1 plant2 plant3");
		testDive.setObservationsList("observation1 observation2");
		
		diveService.save(testDive);
	}
	
	private LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
