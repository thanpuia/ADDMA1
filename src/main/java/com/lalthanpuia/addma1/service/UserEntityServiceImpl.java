package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.UserRepository;
import com.lalthanpuia.addma1.entity.UserEntity;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	private UserRepository userRepository;

	@Autowired
	public UserEntityServiceImpl (UserRepository theUserRepository) {
		userRepository = theUserRepository;
	}
	
	@Override
	public List<UserEntity> findAll() {
			return userRepository.findAll();
	}

	@Override
	public void save(UserEntity theUserEntity) {
		userRepository.save(theUserEntity);
		
	}
	
	@Override
	public UserEntity findById(int theId) {
		
		Optional<UserEntity> result = userRepository.findById(theId);
		
		UserEntity theUserEntity = null;
		if(result.isPresent()) 
			theUserEntity=result.get();
		else
			throw new RuntimeException("Did not find employee id - "+ theId);
		
		return theUserEntity;
	}
	
}
