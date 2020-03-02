package app.services;

import app.models.Dive;

public interface DiveService{
    void save(Dive dive);
    long count();
}
