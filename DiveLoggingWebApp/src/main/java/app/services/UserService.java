package app.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import app.models.User;
import app.web.UserDto;

public interface UserService extends UserDetailsService {
	User findByUsername(String username);
    User findByEmail(String email);
    User findById(long Id);
    Iterable<User> findAll();
    User save(UserDto registration);
    void save(User user);
    long count();
}