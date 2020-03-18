package app.services;

import java.time.LocalDate;

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
}
