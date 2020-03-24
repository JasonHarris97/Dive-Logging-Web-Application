package app.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.models.Dive;
import app.models.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByFirstName(String firstName);
	User findByEmail(String email);
	User findByUsername(String username);
	User findById(long id);
	
	Iterable<User> findByPadiNo(String padiNo);
	Iterable<User> findTop1000ByUsernameContaining(String username, Sort sort);
    Iterable<User> findTop1000ByCountryContaining(String country, Sort sort);
    Iterable<User> findTop1000ByFullNameContaining(String fullName, Sort sort);
    Iterable<User> findTop1000ByPadiLevel(String padiLevel, Sort sort);
    
    Page<User> findByPadiNo(String padiNo, Pageable pageable);
    Page<User> findByUsernameContaining(String username, Pageable pageable);
    Page<User> findByCountryContaining(String country, Pageable pageable);
    Page<User> findByFullNameContaining(String fullName, Pageable pageable);
    Page<User> findByPadiLevel(String padiLevel, Pageable pageable);
}
