package app.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringLists {
	
	private final List<String> padiLevels = new ArrayList<String>(Arrays.asList("Discover Scuba Diving", "Scuba Diver", "Open Water Diver", 
			"Specialty Diver", "Advanced Open Water Diver", "Rescue Diver", "Divemaster Scuba Diver"));
	
	private final List<String> visabilityOptions = new ArrayList<String>(Arrays.asList("Extremely Poor", "Very Poor", "Poor", "Good", "Very Good",
				"Extremely Good"));
	
	private final List<String> waterTypes = new ArrayList<String>(Arrays.asList("Salt", "Fresh", "Brackish", "Brine"));
	
	public StringLists() {	
	}
	
	public List<String> getPadiLevels(){
		return padiLevels;
	}
	
	public List<String> getVisabilityOptions(){
		return visabilityOptions;
	}
	
	public List<String> getWaterTypes(){
		return waterTypes;
	}
}
