package app.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.models.Dive;
import app.models.User;
import app.repository.DiveRepository;

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
	
	public Dive findByUser(User user) {
		return diveRepository.findByDiveOwner(user);
	}
	
	public Dive findById(long Id) {
		return diveRepository.findById(Id);
	}
}
