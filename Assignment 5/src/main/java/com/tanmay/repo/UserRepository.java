package com.tanmay.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tanmay.model.User;
/**
 * @author tanmaysowdhaman
 * Dec 14, 2017
 */
public interface UserRepository extends JpaRepository<User, Long>{
	
	 User findByUsername(String username);

}
