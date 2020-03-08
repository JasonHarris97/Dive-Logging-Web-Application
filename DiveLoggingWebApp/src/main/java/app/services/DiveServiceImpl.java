package app.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.models.Dive;
import app.models.User;
import app.repository.DiveRepository;

@Service
public class DiveServiceImpl implements DiveService{
	@Autowired 
	DiveRepository diveRepository;
	
	@Override
	public long count() {
		return diveRepository.count();
	}

	@Override
	public void save(Dive dive) {
		diveRepository.save(dive);
	}
	
	@Override
	public Dive findByUser(User user) {
		return diveRepository.findByDiveOwner(user);
	}
	
	@Override
	public Dive findById(long Id) {
		return diveRepository.findById(Id);
	}
	
	@Override
	public Iterable<Dive> findAll() {
		return diveRepository.findAll();
	}
	
	@Override
	public Iterable<Dive> findFifty() {
		return diveRepository.findFifty();
	}

	@Override
	public Iterable<Dive> findAllByDiveOwner(User diveOwner) {
		return diveRepository.findTop1000ByDiveOwner(diveOwner);
	}

	@Override
	public Iterable<Dive> findAllByCountry(String country) {
		return diveRepository.findTop1000ByCountry(country);
	}

	@Override
	public Iterable<Dive> findAllByDate(LocalDate date) {
		return diveRepository.findTop1000ByDate(date);
	}
	
	@Override
	public Iterable<Dive> findAllByLocation(String location) {
		return diveRepository.findTop1000ByLocation(location);
	}

	@Override
	public Iterable<Dive> findAllByDiveOwnerPadiLevel(String padiLevel) {
		return diveRepository.findTop1000ByDiveOwnerPadiLevel(padiLevel);
	}

	@Override
	public Iterable<Dive> findAllByDiveOwner(User diveOwner, Sort sort) {
		return diveRepository.findTop1000ByDiveOwner(diveOwner, sort);
	}

	@Override
	public Iterable<Dive> findAllByCountry(String country, Sort sort) {
		return diveRepository.findTop1000ByCountry(country, sort);
	}

	@Override
	public Iterable<Dive> findAllByDate(LocalDate date, Sort sort) {
		return diveRepository.findTop1000ByDate(date, sort);
	}

	@Override
	public Iterable<Dive> findAllByLocation(String location, Sort sort) {
		return diveRepository.findTop1000ByLocation(location, sort);
	}

	@Override
	public Iterable<Dive> findAllByDiveOwnerPadiLevel(String padiLevel, Sort sort) {
		return diveRepository.findTop1000ByDiveOwnerPadiLevel(padiLevel, sort);
	}

	@Override
	public Iterable<Dive> findAll(Sort sort) {
		return diveRepository.findAll(sort);
	}
	
	@Override
	public Page<Dive> findAll(Pageable pageable) {
		return diveRepository.findAll(pageable);
	}

	@Override
	public Page<Dive> findAllByDiveOwner(User diveOwner, Pageable pageable) {
		return diveRepository.findTop1000ByDiveOwner(diveOwner, pageable);
	}

	@Override
	public Page<Dive> findAllByCountry(String country, Pageable pageable) {
		return diveRepository.findTop1000ByCountry(country, pageable);
	}

	@Override
	public Page<Dive> findAllByDate(LocalDate date, Pageable pageable) {
		return diveRepository.findTop1000ByDate(date, pageable);
	}

	@Override
	public Page<Dive> findAllByLocation(String location, Pageable pageable) {
		return diveRepository.findTop1000ByLocation(location, pageable);
	}

	@Override
	public Page<Dive> findAllByDiveOwnerPadiLevel(String padiLevel, Pageable pageable) {
		return diveRepository.findTop1000ByDiveOwnerPadiLevel(padiLevel, pageable);
	}
}
