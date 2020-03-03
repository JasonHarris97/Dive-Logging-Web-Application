package app.repository;

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
}
