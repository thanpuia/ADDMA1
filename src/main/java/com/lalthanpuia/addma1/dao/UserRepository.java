 package com.lalthanpuia.addma1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lalthanpuia.addma1.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	//Optional<User> findByUsername(String theUsername);	//List<User> findByUsername(String theUsername);
	public List<User> findByUsername(String theUsername);	//List<User> findByUsername(String theUsername);

	//@Query(value = "SELECT u FROM User u ORDER BY phoneNo")
	public List<User> findByPhoneNo (String thePhoneNo);
	
	//	Optional<User> findByPhoneNo(String thePhoneNo);
	//List<User> findByPhoneNo(String thePhoneNo);
		
}


