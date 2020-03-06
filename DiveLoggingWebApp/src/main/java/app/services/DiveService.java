package app.services;

import app.models.Dive;
import app.models.User;

public interface DiveService{
    void save(Dive dive);
    long count();
    Dive findByUser(User user);
    Dive findById(long Id);
    Iterable<Dive> findAll();
    Iterable<Dive> findFifty();
}
