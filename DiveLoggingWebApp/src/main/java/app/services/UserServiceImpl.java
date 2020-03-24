package app.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.models.Role;
import app.models.User;
import app.repository.UserRepository;
import app.web.UserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User findByUsername(String username) {
    	return userRepository.findByUsername(username);
    }
    
    @Override
    public Iterable<User> findByUsernameContaining(String username, Sort sort) {
    	return userRepository.findTop1000ByUsernameContaining(username, sort);
    }
    
    @Override
    public User findById(long id) {
    	return userRepository.findById(id);
    }

    @Override
    public User save(UserDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setUsername(registration.getUsername());
        user.setFullName(registration.getFirstName()+" "+registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setContactNumber(registration.getContactNumber());
        user.setCountry(registration.getCountry());
        user.setPadiLevel(registration.getPadiLevel());
        user.setPadiNo(registration.getPadiNo());
        user.setNoOfDives(0);
        user.setNoOfCountries(0);
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
    
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String identification) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(identification);
        if(user == null){
        	user = userRepository.findByUsername(identification);
        }
        
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(),
            mapRolesToAuthorities(user.getRoles()));
    }
    
    @Override
    public long count() {
        return userRepository.count();
    }

    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection <Role> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Iterable<User> findByPadiNo(String padiNo) {
		return userRepository.findByPadiNo(padiNo);
	}

	@Override
	public Iterable<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public Iterable<User> findAllByCountry(String country, Sort sort) {
		return userRepository.findTop1000ByCountryContaining(country, sort);
	}

	@Override
	public Iterable<User> findAllByName(String fullName, Sort sort) {
		return userRepository.findTop1000ByFullNameContaining(fullName, sort);
	}

	@Override
	public Iterable<User> findAllByPadiLevel(String padiLevel, Sort sort) {
		return userRepository.findTop1000ByPadiLevel(padiLevel, sort);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> findByPadiNo(String padiNo, Pageable pageable) {
		return userRepository.findByPadiNo(padiNo, pageable);
	}

	@Override
	public Page<User> findByUsernameContaining(String username, Pageable pageable) {
		return userRepository.findByUsernameContaining(username, pageable);
	}

	@Override
	public Page<User> findByCountry(String country, Pageable pageable) {
		return userRepository.findByCountryContaining(country, pageable);
	}

	@Override
	public Page<User> findByName(String fullName, Pageable pageable) {
		return userRepository.findByFullNameContaining(fullName, pageable);
	}

	@Override
	public Page<User> findByPadiLevel(String padiLevel, Pageable pageable) {
		return userRepository.findByPadiLevel(padiLevel, pageable);
	}
}