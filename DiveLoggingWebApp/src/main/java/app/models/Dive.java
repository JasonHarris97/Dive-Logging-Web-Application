package app.models;

import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dive")
public class Dive {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotNull
	@OneToOne
	private User diveOwner;
	
	private Date date;
	
	private Time startTime;
	
	private Time endTime;
	
	private Time diveTimeLength;
	
	private double latitude;
	
	private double longitude;
	
	private String country;
	
	private String location;
	
	private String detailedLocation;
	
	private String weather;
	
	private String visibility;
	
	private String waterType;
	
	private String diveSuit;
	
	private String entry;
	
	private double airTemperature;
	
	private double waterTemperature;
	
	private String tankType;
	
	private String gasType;
	
	private double tankSize;
	
	private double tankStart;
	
	private double tankEnd;
	
	private double tankUsage;
	
	private int noOfParticipants;
	
	private String participantsList;
	
	private double maxDepth;
	
	private double altitude;
	
	private String description;
	
	private String faunaList;
	
	private String floraList;
	
	private String observationsList;
	
	// Id -----------------------------------------
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // Id -----------------------------------------
    
    // diveOwner -----------------------------------------
 	public User getDiveOwner() {
         return diveOwner;
    }

	public void setDiveOwn(User diveOwner) {
	    this.diveOwner = diveOwner;
	}
	// diveOwner -----------------------------------------
 
	// date -----------------------------------------
	public Date getDate() {
	     return date;
	}
	
	public void setDate(Date date) {
	     this.date = date;
	}
	// date -----------------------------------------
      
	// startTime -----------------------------------------
	public Time getStartTime() {
	     return startTime;
	}
	
	public void setStartTime(Time startTime) {
	     this.startTime = startTime;
	}
	// startTime -----------------------------------------

	// endTime -----------------------------------------
	public Time getEndTime() {
	     return endTime;
	}
	
	public void setEndTime(Time endTime) {
	     this.endTime = endTime;
	}
	// endTime -----------------------------------------
	
	// diveTimeLength -----------------------------------------
	public Time getDiveTimeLength() {
	     return diveTimeLength;
	}
	
	public void setDiveTimeLength(Time diveTimeLength) {
	     this.diveTimeLength = diveTimeLength;
	}
	// diveTimeLength -----------------------------------------

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetailedLocation() {
		return detailedLocation;
	}

	public void setDetailedLocation(String detailedLocation) {
		this.detailedLocation = detailedLocation;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getWaterType() {
		return waterType;
	}

	public void setWaterType(String waterType) {
		this.waterType = waterType;
	}

	public String getDiveSuit() {
		return diveSuit;
	}

	public void setDiveSuit(String diveSuit) {
		this.diveSuit = diveSuit;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public double getAirTemperature() {
		return airTemperature;
	}

	public void setAirTemperature(double airTemperature) {
		this.airTemperature = airTemperature;
	}

	public double getWaterTemperature() {
		return waterTemperature;
	}

	public void setWaterTemperature(double waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public String getTankType() {
		return tankType;
	}

	public void setTankType(String tankType) {
		this.tankType = tankType;
	}

	public String getGasType() {
		return gasType;
	}

	public void setGasType(String gasType) {
		this.gasType = gasType;
	}

	public double getTankSize() {
		return tankSize;
	}

	public void setTankSize(double tankSize) {
		this.tankSize = tankSize;
	}

	public double getTankStart() {
		return tankStart;
	}

	public void setTankStart(double tankStart) {
		this.tankStart = tankStart;
	}

	public double getTankEnd() {
		return tankEnd;
	}

	public void setTankEnd(double tankEnd) {
		this.tankEnd = tankEnd;
	}

	public double getTankUsage() {
		return tankUsage;
	}

	public void setTankUsage(double tankUsage) {
		this.tankUsage = tankUsage;
	}

	public int getNoOfParticipants() {
		return noOfParticipants;
	}

	public void setNoOfParticipants(int noOfParticipants) {
		this.noOfParticipants = noOfParticipants;
	}

	public String getParticipantsList() {
		return participantsList;
	}

	public void setParticipantsList(String participantsList) {
		this.participantsList = participantsList;
	}

	public double getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(double maxDepth) {
		this.maxDepth = maxDepth;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFaunaList() {
		return faunaList;
	}

	public void setFaunaList(String faunaList) {
		this.faunaList = faunaList;
	}

	public String getFloraList() {
		return floraList;
	}

	public void setFloraList(String floraList) {
		this.floraList = floraList;
	}

	public String getObservationsList() {
		return observationsList;
	}

	public void setObservationsList(String observationsList) {
		this.observationsList = observationsList;
	}
	
}
