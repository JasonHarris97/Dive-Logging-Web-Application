package app.services;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;

import app.models.User;
import app.web.UserDto;

public interface UserService extends UserDetailsService {
	User findByUsername(String username);
    User findByEmail(String email);
    User findById(long Id);
    User save(UserDto registration);
    
    void save(User user);
    long count();
    
    Iterable<User> findAll();
    Iterable<User> findByPadiNo(String padiNo);
    
    Iterable<User> findAll(Sort sort);
    
    Iterable<User> findAllByCountry(String country, Sort sort);
    Iterable<User> findAllByName(String fullName, Sort sort);
    Iterable<User> findAllByPadiLevel(String padiLevel, Sort sort);   
}