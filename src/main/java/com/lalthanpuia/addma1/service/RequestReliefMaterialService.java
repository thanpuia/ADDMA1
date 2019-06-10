package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.Relief;

public interface RequestReliefMaterialService {

	public List<Relief> findAll();
	
	public Relief findById (int theId);
	
	public void save (Relief theRequestReliefMaterialEntity);
	
	public void deleteById (int theId);
	
	
	public List<Relief> findByPhone(String thePhone);
	
	public Relief findFirst1ByOrderBySerialNumberDesc();
	
	public List<Relief> findByOfficerId(String theOfficerId);
	
	public List<Relief> findByOfficerContact(String theOfficerContact);
	
	//public Incident findBySerialNumber(int theSerialNumber, String status);
	public Relief findBySerialNumber(int theSerialNumber);

}
