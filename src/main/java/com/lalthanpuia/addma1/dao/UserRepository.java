 package com.lalthanpuia.addma1.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	//Optional<User> findByUsername(String theUsername);
	List<User> findByUsername(String theUsername);
	
//	Optional<User> findByPhoneNo(String thePhoneNo);
	List<User> findByPhoneNo(String thePhoneNo);
		
}
