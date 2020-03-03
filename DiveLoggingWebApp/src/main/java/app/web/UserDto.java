package app.web;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import app.constraint.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
    @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
    
    @NotEmpty
    private String username;
    
    @Email
    @NotEmpty
    private String email;
    
    @Email
    @NotEmpty
    private String confirmEmail;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
    
    @NotEmpty
    private String contactNumber;
    
    @NotEmpty
    private String country;
    
    @NotEmpty
    private String padiLevel;

    @AssertTrue
    private Boolean terms;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
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
}

