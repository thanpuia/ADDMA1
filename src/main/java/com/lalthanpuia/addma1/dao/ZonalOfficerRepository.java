package com.lalthanpuia.addma1.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.Officer;

public interface ZonalOfficerRepository extends JpaRepository<Officer, Integer> {

	Optional<Officer> findByOfficerDistrict(String theDistrict);
	
	Optional<Officer> findByOfficerZone(String theZone);

	Optional<Officer> findByOfficerLocality(String theLocality);


}
