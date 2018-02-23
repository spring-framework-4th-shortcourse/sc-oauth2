package org.sc.oauth2.resource.server.repository;

import org.sc.oauth2.resource.server.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	User findByUsername(String username);
	
}
