package app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.models.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	User findByFirstName(String firstName);
	User findByEmail(String email);
}
