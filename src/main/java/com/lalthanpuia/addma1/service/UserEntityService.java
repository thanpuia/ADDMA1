package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import com.lalthanpuia.addma1.entity.User;

public interface UserEntityService {

	public List<User> findAll();
		
	public void save(User theUserEntity);
	
	public User findByUsername(String theUsername);
	
	public User findByPhoneNo(String thePhoneNo);
}
