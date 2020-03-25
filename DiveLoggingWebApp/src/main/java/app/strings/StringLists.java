package app.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StringLists {
	
	private final List<String> padiLevels = new ArrayList<String>(Arrays.asList("Not Acquired", "Discover Scuba Diving", "Scuba Diver", "Open Water Diver", 
			"Specialty Diver", "Advanced Open Water Diver", "Rescue Diver", "Divemaster Scuba Diver"));
	
	private final List<String> visibilityTypes = new ArrayList<String>(Arrays.asList("Unkown", "Extremely Poor", "Very Poor", "Poor", "Good", "Very Good",
				"Extremely Good"));
	
	private final List<String> tideLevels = new ArrayList<String>(Arrays.asList("None", "High", "Low", "Inbetween"));
	
	private final List<String> waterTypes = new ArrayList<String>(Arrays.asList("Unknown", "Salt", "Fresh", "Brackish", "Brine"));
	
	private final List<String> waterBodyTypes = new ArrayList<String>(Arrays.asList("Unknown", "River", "Lake", "Pond", "Ocean", "Sea", "Estuary", "Arroyo", "Bay", "Bayou", "Beck", 
			"Bight", "Billabong", "Seep", "Creek", "Canal", "Channel", "Cove", "Delta", "Firth", "Fjord", "Gill", "Harbour", "Lagoon", "Loch", "Mere", "Reservoir", "Sound", 
			"Spring", "Strait", "Subglacial Lake", "Swamp", "Tarn", "Tide Pool", "Tributary", "Vernal Pool"));
	
	private final List<String> countries;
	
	private final List<String> weatherTypes = new ArrayList<String>(Arrays.asList("Unknown", "Clear Night", "Clear Day", "Sunny Day", "Partly Cloudy", "Sunny Intervals", "Mist", "Fog", "Cloudy", "Overcast",
			"Light Rain", "Light Rain Showers", "Drizzle", "Heavy Rain", "Heavy Rain Showers", "Sleet", "Sleet Showers", "Hail", "Hail Showers", "Light Snow", "Light Snow Showers",
			"Heavy Snow", "Heavy Snow Showers", "Thunder", "Thundery Showers"));
	
	private final List<String> entryMethods = new ArrayList<String>(Arrays.asList("Unknown", "Beach", "Shoreline", "Small Boat", "Large Boat", "Pier", "Jetty", "Other"));
	
	private final List<String> divingSuitTypes = new ArrayList<String>(Arrays.asList("Unknown", "Wetsuit", "7mm Wetsuit", "5mm Wetsuit", "3mm Wetsuit", "Shorty Wetsuit",
			"Dry Suit", "Semi-dry Suit", "Dive Skin", "Armored Suit", "Atmospheric Pressure Suit", "Other"));
	
	
	
	public StringLists() {	
		Collections.sort(waterBodyTypes);
		Collections.sort(padiLevels);
		countries = getListOfCountries();
		Collections.sort(countries);
	}
	
	public List<String> getPadiLevels(){
		return padiLevels;
	}
	
	public List<String> getVisibilityTypes(){
		return visibilityTypes;
	}
	
	public List<String> getWaterTypes(){
		return waterTypes;
	}

	public List<String> getWaterBodyTypes() {
		return waterBodyTypes;
	}

	public List<String> getTideLevels() {
		return tideLevels;
	}

	public List<String> getCountries() {
		return countries;
	}
	
	private ArrayList<String> getListOfCountries() {
		ArrayList<String> listOfCountries = new ArrayList<String>();
		
		String[] locales = Locale.getISOCountries();
		
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);

			listOfCountries.add(obj.getDisplayCountry());
		}
		
		return listOfCountries;
	}

	public List<String> getWeatherTypes() {
		return weatherTypes;
	}

	public List<String> getEntryMethods() {
		return entryMethods;
	}

	public List<String> getDivingSuitTypes() {
		return divingSuitTypes;
	}
}
