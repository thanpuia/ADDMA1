package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.UserRepository;
import com.lalthanpuia.addma1.entity.User;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	private UserRepository userRepository;

	@Autowired
	public UserEntityServiceImpl (UserRepository theUserRepository) {
		userRepository = theUserRepository;
	}
	
	@Override
	public List<User> findAll() {
			return userRepository.findAll();
	}

	@Override
	public void save(User theUserEntity) {
		userRepository.save(theUserEntity);
		
	}

	@Override
	public User findByUsername(String theUsername) {
		
//		Optional<User> result = userRepository.findByUsername(theUsername);
//		//List<User> result = userRepository.findByUsername(theUsername);
//	
//		
//		User theUserEntity= null;
//		System.out.println("Username in the entity service: "+ theUserEntity.getUsername());
//		
//		if(result.isPresent())
//			theUserEntity=result.get();
//		else
//			throw new RuntimeException("Did not find employee id - "+ theUsername);
//		
//		return theUserEntity;
		
		List<User> result = null;
		try{
			result = userRepository.findByUsername(theUsername);	
		}catch(Exception e) {
			
			
		}
		
		//List<User> result = userRepository.findByUsername(theUsername);
	
		
//		User theUserEntity= null;
//		System.out.println("Username in the entity service: "+ theUserEntity.getUsername());
//		
//		if(result.isPresent())
//			theUserEntity=result.get();
//		else
//			throw new RuntimeException("Did not find employee id - "+ theUsername);
		
		return result.get(0);
	}
	
	/*
	 * @Override public User findByPhoneNo(String thePhoneNo) {
	 * 
	 * Optional<User> result = userRepository.findByPhoneNo(thePhoneNo);
	 * 
	 * User theUserEntity= null;
	 * 
	 * if(result.isPresent()) theUserEntity=result.get(); else throw new
	 * RuntimeException("Did not find employee id - "+ thePhoneNo);
	 * 
	 * return theUserEntity;
	 * 
	 * }
	 */
	
	
}
