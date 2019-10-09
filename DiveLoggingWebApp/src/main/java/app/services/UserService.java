package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.models.User;
import app.repository.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;
	
    @Autowired
    public UserService(UserRepository userRepository) { 
      this.userRepository = userRepository;
    }
    
    public User findByFirstName(String firstName) {
    	return userRepository.findByFirstName(firstName);
    }
	
	public void saveUser(User user) {
		userRepository.save(user);
	}

}