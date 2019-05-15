package com.lalthanpuia.addma1.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.Officer;

public interface ZonalOfficerRepository extends JpaRepository<Officer, Integer> {

	Optional<Officer> findByDistrict(String theDistrict);
	
	Optional<Officer> findByZone(String theZone);



}
