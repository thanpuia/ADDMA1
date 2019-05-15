package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.ZonalOfficerRepository;
import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.entity.Officer;

@Service
public class ZonalOfficerServiceImpl implements ZonalOfficerService {

	private ZonalOfficerRepository zonalOfficerRepository;

	@Autowired
	public ZonalOfficerServiceImpl (ZonalOfficerRepository theZonalOfficerRepository) {
		zonalOfficerRepository = theZonalOfficerRepository;
	}

	@Override
	public List<Officer> findAll() {
		// TODO Auto-generated method stub
		return zonalOfficerRepository.findAll();
	}

	public Officer findByDistrict(String theDistrict) {
		Optional <Officer> result = zonalOfficerRepository.findByDistrict(theDistrict);
		
		Officer theZonalOfficer =null;
		
		if(result.isPresent())
			theZonalOfficer = result.get();
		else
			throw new RuntimeException ("Did not find-"+theDistrict);
		
		return theZonalOfficer;
	}

	@Override
	public Officer findByZone(String theZone) {
	Optional <Officer> result = zonalOfficerRepository.findByZone(theZone);
		
		Officer theZonalOfficer =null;
		
		if(result.isPresent())
			theZonalOfficer = result.get();
		else
			throw new RuntimeException ("Did not find-"+theZone);
		
		return theZonalOfficer;
	}
}
