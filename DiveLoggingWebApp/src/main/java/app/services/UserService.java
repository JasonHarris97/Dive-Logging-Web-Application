package app.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;

import app.models.Dive;
import app.models.User;
import app.web.UserDto;

public interface UserService extends UserDetailsService {
	User findByUsername(String username);
    User findByEmail(String email);
    User findById(long Id);
    User save(UserDto registration);
    
    void setToDefaultProfile(User testUser);
    
    
    void save(User user);
    long count();
    
    Iterable<User> findAll();
    
    Iterable<User> findAll(Sort sort);
    Iterable<User> findByPadiNo(String padiNo);
    Iterable<User> findByUsernameContaining(String username, Sort sort);
    Iterable<User> findAllByCountry(String country, Sort sort);
    Iterable<User> findAllByName(String fullName, Sort sort);
    Iterable<User> findAllByPadiLevel(String padiLevel, Sort sort);   
    
    Page<User> findAll(Pageable pageable);
    Page<User> findByPadiNo(String padiNo, Pageable pageable);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findByCountry(String country, Pageable pageable);
    Page<User> findByName(String fullName, Pageable pageable);
    Page<User> findByPadiLevel(String padiLevel, Pageable pageable);
}