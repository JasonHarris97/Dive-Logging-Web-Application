package app.services;

import org.springframework.beans.factory.annotation.Autowired;
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
}
