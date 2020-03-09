// SOURCE: https://www.codebyamir.com/blog/user-account-registration-with-spring-boot

package app.models;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//d

    private String firstName;//d
    
    private String lastName;//d
    
    private String username;//d
    
    private String email;//d
    
    private String password;//d
    
    private String contactNumber;//d
    
    private String country;//d
    
    private String padiLevel;//d
    
    private String padiNo; //d
    
    private int noOfDives;//d
    
    private int noOfCountries;//d
    
	@OneToMany(mappedBy = "fileOwner")
	private List<DBFile> images = new ArrayList<DBFile>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "id"))
    private Collection < Role > roles;

    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, Collection < Role > roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection < Role > getRoles() {
        return roles;
    }

    public void setRoles(Collection < Role > roles) {
        this.roles = roles;
    }

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPadiLevel() {
		return padiLevel;
	}

	public void setPadiLevel(String padiLevel) {
		this.padiLevel = padiLevel;
	}

	public int getNoOfDives() {
		return noOfDives;
	}

	public void setNoOfDives(int noOfDives) {
		this.noOfDives = noOfDives;
	}

	public int getNoOfCountries() {
		return noOfCountries;
	}

	public void setNoOfCountries(int noOfCountries) {
		this.noOfCountries = noOfCountries;
	}
	
    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + "*********" + '\'' +
            ", roles=" + roles +
            '}';
    }

	public String getPadiNo() {
		return padiNo;
	}

	public void setPadiNo(String padiNo) {
		this.padiNo = padiNo;
	}
	
	public List<DBFile> getImages () {
		return images;
	}

	public void setFiles(List<DBFile> images) {
		this.images = images;
	}
}