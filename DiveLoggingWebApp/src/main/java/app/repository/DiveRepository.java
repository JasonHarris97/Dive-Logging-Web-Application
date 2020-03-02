package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.models.Dive;
import app.models.User;

@Repository("DiveRepository")
public interface DiveRepository extends JpaRepository<Dive, Long> {
	Dive findByDiveOwner(User diveOwner);
	Dive findById(long id);
}