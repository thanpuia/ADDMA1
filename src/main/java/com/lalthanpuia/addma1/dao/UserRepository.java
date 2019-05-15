 package com.lalthanpuia.addma1.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String theUsername);
			
}
