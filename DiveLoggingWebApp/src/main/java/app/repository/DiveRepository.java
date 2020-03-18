package app.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.models.Dive;
import app.models.User;

@Repository("DiveRepository")
public interface DiveRepository extends JpaRepository<Dive, Long> {
	Dive findByDiveOwner(User diveOwner);
	Dive findById(long id);
	
	@Query(value = "SELECT * FROM dive LIMIT 50", nativeQuery = true)
	Iterable<Dive> findFifty();
	
	Iterable<Dive> findTop1000ByDiveOwner(User diveOwner);
	Iterable<Dive> findTop1000ByDate(LocalDate date);
	Iterable<Dive> findTop1000ByCountry(String country);
	Iterable<Dive> findTop1000ByLocation(String location);
	Iterable<Dive> findTop1000ByDiveOwnerPadiLevel(String padiLevel);
	
	Iterable<Dive> findTop1000ByDiveOwnerFullNameContaining(String fullName, Sort sort);
	Iterable<Dive> findTop1000ByCountryContaining(String country, Sort sort);
	Iterable<Dive> findTop1000ByDate(LocalDate date, Sort sort);
	Iterable<Dive> findTop1000ByDiveOwnerPadiLevel(String padiLevel, Sort sort);
	Iterable<Dive> findTop1000ByLocationContaining(String location, Sort sort);
}