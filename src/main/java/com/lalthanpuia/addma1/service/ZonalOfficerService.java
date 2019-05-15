package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.Officer;

public interface ZonalOfficerService {

	public List<Officer> findAll();
	
	public  Officer findByDistrict(String theDistrict);
	
	public  Officer findByZone(String theZone);

	
}
