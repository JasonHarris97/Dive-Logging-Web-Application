package app.services;

import java.time.LocalDate;

import app.models.Dive;
import app.models.User;

public interface DiveService{
    void save(Dive dive);
    long count();
    Dive findByUser(User user);
    Dive findById(long Id);
    Iterable<Dive> findAllByDiveOwner(User diveOwner);
    Iterable<Dive> findAllByCountry(String country);
    Iterable<Dive> findAllByDate(LocalDate date);
    Iterable<Dive> findAllByLocation(String location);
    Iterable<Dive> findAll();
    Iterable<Dive> findFifty();
}
