package app.services;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import app.models.Dive;
import app.models.User;

public interface DiveService{
    void save(Dive dive);
    long count();
    
    Dive findByUser(User user);
    Dive findById(long Id);
    
    Iterable<Dive> findAll();
    Iterable<Dive> findAll(Sort sort);
    
    Iterable<Dive> findFifty();
    
    Iterable<Dive> findAllByDiveOwner(User diveOwner);
    Iterable<Dive> findAllByCountry(String country);
    Iterable<Dive> findAllByDate(LocalDate date);
    Iterable<Dive> findAllByLocation(String location);
    Iterable<Dive> findAllByDiveOwnerPadiLevel(String padiLevel);
    
    Iterable<Dive> findAllByDiveOwner(String fullName, Sort sort);
    Iterable<Dive> findAllByCountry(String country, Sort sort);
    Iterable<Dive> findAllByDate(LocalDate date, Sort sort);
    Iterable<Dive> findAllByLocation(String location, Sort sort);
    Iterable<Dive> findAllByDiveOwnerPadiLevel(String padiLevel, Sort sort);
    
    Page<Dive> findAll(Pageable pageable);
    Page<Dive> findAllByDiveOwnerUsername(String username, Pageable pageable);
    Page<Dive> findAllByDiveOwner(String fullName, Pageable pageable);
    Page<Dive> findAllByCountry(String country, Pageable pageable);
    Page<Dive> findAllByDate(LocalDate date, Pageable pageable);
    Page<Dive> findAllByLocation(String location, Pageable pageable);
    Page<Dive> findAllByDiveOwnerPadiLevel(String padiLevel, Pageable pageable);

}
