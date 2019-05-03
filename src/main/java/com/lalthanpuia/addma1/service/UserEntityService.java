package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import com.lalthanpuia.addma1.entity.UserEntity;

public interface UserEntityService {

	public List<UserEntity> findAll();
		
	public void save(UserEntity theUserEntity);
	
	public UserEntity findById(int theId);
}
