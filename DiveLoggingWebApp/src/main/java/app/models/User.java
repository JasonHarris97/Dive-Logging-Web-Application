// SOURCE: https://www.codebyamir.com/blog/user-account-registration-with-spring-boot

package app.models;

import java.util.Collection;

/*import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;*/

import javax.persistence.*;


import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password")
	@Transient
	private String password;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "confirmation_token")
	private String confirmationToken;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	   @JoinTable(
	        name = "users_roles",
	        joinColumns = @JoinColumn(
	            name = "user_id", referencedColumnName = "id"),
	        inverseJoinColumns = @JoinColumn(
	            name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	
	// Constructors
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
	
	// Getters and Setters 
	// -------------------
	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public void setLastName(String username) {
		this.lastName = lastName;
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
    
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean value) {
		this.enabled = value;
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
}