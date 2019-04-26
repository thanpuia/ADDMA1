 package com.lalthanpuia.addma1.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
