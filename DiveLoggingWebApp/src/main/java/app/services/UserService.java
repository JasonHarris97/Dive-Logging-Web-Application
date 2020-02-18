package app.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import app.models.User;
import app.web.UserDto;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User save(UserDto registration);
}