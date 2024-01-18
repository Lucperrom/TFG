package org.springframework.samples.pubus.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends  CrudRepository<User, String>{

	@Query("SELECT u FROM User u WHERE u.username = :username")
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	
	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Optional<User> findById(Integer id);
	
	@Query("SELECT u FROM User u WHERE u.authority = :auth")
	Iterable<User> findAllByAuthority(String auth);

	@Query("SELECT DISTINCT u.authority FROM User u")
	List<String> findAllAuthorities();
	
}
