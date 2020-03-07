package app.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "dive")
public class Dive {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // done
	
	private int diveNo; // TODO IMPLEMENT IN CONTROLLER
	
	private String diveTitle; // done
	
	private String diveSite; // done
	
	private String divePurpose; // done 
	
	@NotNull
	@OneToOne
	private User diveOwner; // done
	
	@OneToOne
	private User diveBuddy; // TODO IMPLEMENT IN CONTROLLER
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date; // done
	
	private LocalTime startTime; // done
	
	private LocalTime endTime; // done
	
	private Duration diveDuration; // done
	
	private double latitude; // done
	
	private double longitude; // done
	
	private String country; // done
	
	private String location; // done
	
	@Column(columnDefinition="varchar(2000)")
	private String detailedLocation;  // done
	
	@Column(columnDefinition="varchar(2000)")
	private String weather; // done
	
	private String visibility; // done
	
	private String waterType; // done
	
	private String waterBodyType; // done
	
	private double waterDensity; // done
	
	private String tideLevel; // done
	
	private String diveSuit; // done
	
	private String entry; // done
	
	private double airTemperature; // done
	
	private double waterTemperature; // done
	
	private String tankType; // done
	
	private String gasType; // done
	
	private String gasMix; // done
	
	private double fractionOfHelium; // done
	
	private double fractionOfNitrogen; // done
	
	private int tankSize; // done
	
	private double tankStart; // done
	
	private double tankEnd; // done
	
	private double tankUsage; // done
	
	private double weightCarried; // done
	
	private int noOfParticipants; // done
	
	@Column(columnDefinition="varchar(2000)")
	private String participantsList;  // done
	
	private String equipment;  // done
	
	private String buoyancy;  // done
	
	private double bottomTime;  // done
	
	private double maxDepth;  // done
	
	private double altitude;  // done
	
	private String startcode; // done
	
	private String surfacingCode; // done
	
	@Column(columnDefinition="varchar(2000)")
	private String description; // done
	
	@Column(columnDefinition="varchar(500)")
	private String faunaList; // done
	
	@Column(columnDefinition="varchar(500)")
	private String floraList; // done
	
	@Column(columnDefinition="varchar(500)")
	private String observationsList; // done
	
	@OneToMany
	private Collection<DBFile> images;
	
	// Constructors
	public Dive() {
	}
	
	public Dive(User diveOwner, String description) {
		this.diveOwner = diveOwner;
		this.description = description;
	}
	
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

	public void setDiveOwner(User diveOwner) {
	    this.diveOwner = diveOwner;
	}
	// diveOwner -----------------------------------------
 
	// date -----------------------------------------
	public LocalDate getDate() {
	     return date;
	}
	
	public void setDate(LocalDate date) {
	     this.date = date;
	}
	// date -----------------------------------------
      
	// startTime -----------------------------------------
	public LocalTime getStartTime() {
	     return startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
	     this.startTime = startTime;
	}
	// startTime -----------------------------------------

	// endTime -----------------------------------------
	public LocalTime getEndTime() {
	     return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
	     this.endTime = endTime;
	}
	// endTime -----------------------------------------
	
	// diveTimeLength -----------------------------------------
	public Duration getDiveDuration() {
	     return diveDuration;
	}
	
	public void setDiveDuration(Duration diveDuration) {
	     this.diveDuration = diveDuration;
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

	public int getTankSize() {
		return tankSize;
	}

	public void setTankSize(int tankSize) {
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

	public String getWaterBodyType() {
		return waterBodyType;
	}

	public void setWaterBodyType(String waterBodyType) {
		this.waterBodyType = waterBodyType;
	}

	public double getFractionOfHelium() {
		return fractionOfHelium;
	}

	public void setFractionOfHelium(double fractionOfHelium) {
		this.fractionOfHelium = fractionOfHelium;
	}

	public double getFractionOfNitrogen() {
		return fractionOfNitrogen;
	}

	public void setFractionOfNitrogen(double fractionOfNitrogen) {
		this.fractionOfNitrogen = fractionOfNitrogen;
	}

	public User getDiveBuddy() {
		return diveBuddy;
	}

	public void setDiveBuddy(User diveBuddy) {
		this.diveBuddy = diveBuddy;
	}

	public double getWaterDensity() {
		return waterDensity;
	}

	public void setWaterDensity(double waterDensity) {
		this.waterDensity = waterDensity;
	}

	public int getDiveNo() {
		return diveNo;
	}

	public void setDiveNo(int diveNo) {
		this.diveNo = diveNo;
	}

	public String getDiveSite() {
		return diveSite;
	}

	public void setDiveSite(String diveSite) {
		this.diveSite = diveSite;
	}

	public String getDivePurpose() {
		return divePurpose;
	}

	public void setDivePurpose(String divePurpose) {
		this.divePurpose = divePurpose;
	}

	public String getTideLevel() {
		return tideLevel;
	}

	public void setTideLevel(String tideLevel) {
		this.tideLevel = tideLevel;
	}

	public String getGasMix() {
		return gasMix;
	}

	public void setGasMix(String gasMix) {
		this.gasMix = gasMix;
	}

	public String getStartcode() {
		return startcode;
	}

	public void setStartcode(String startcode) {
		this.startcode = startcode;
	}

	public String getSurfacingCode() {
		return surfacingCode;
	}

	public void setSurfacingCode(String surfacingCode) {
		this.surfacingCode = surfacingCode;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public double getWeightCarried() {
		return weightCarried;
	}

	public void setWeightCarried(double weightCarried) {
		this.weightCarried = weightCarried;
	}

	public double getBottomTime() {
		return bottomTime;
	}

	public void setBottomTime(double bottomTime) {
		this.bottomTime = bottomTime;
	}

	public String getBuoyancy() {
		return buoyancy;
	}

	public void setBuoyancy(String buoyancy) {
		this.buoyancy = buoyancy;
	}

	public String getDiveTitle() {
		return diveTitle;
	}

	public void setDiveTitle(String diveTitle) {
		this.diveTitle = diveTitle;
	}

	public Collection<DBFile> getImages() {
		return images;
	}

	public void setImages(Collection<DBFile> images) {
		this.images = images;
	}
	
}
