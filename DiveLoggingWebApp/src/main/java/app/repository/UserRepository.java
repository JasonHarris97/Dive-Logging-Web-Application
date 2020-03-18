package app.repository;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByFirstName(String firstName);
	User findByEmail(String email);
	User findByUsername(String username);
	User findById(long id);
	
	Iterable<User> findByPadiNo(String padiNo);
	
    Iterable<User> findTop1000ByCountry(String country, Sort sort);
    
    Iterable<User> findTop1000ByFullNameContaining(String string, Sort sort);
    
    Iterable<User> findTop1000ByPadiLevel(String padiLevel, Sort sort);
}
