package com.lalthanpuia.addma1.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.Relief;

public interface RequestReliefMaterialRepository extends JpaRepository<Relief, Integer> {


	public List<Relief> findByPhone(String thePhone);

	public Relief findFirst1ByOrderBySerialNumberDesc();
	
	public List<Relief> findByOfficerId(String theOfficerId);
	public List<Relief> findByOfficerContact(String theOfficerContact);
	
	//public Incident findBySerialNumber(int theSerialNumber, String status);
	public Relief findBySerialNumber(int theSerialNumber);
}
