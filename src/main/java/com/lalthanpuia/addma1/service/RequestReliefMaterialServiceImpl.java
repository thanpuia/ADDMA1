package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.RequestReliefMaterialRepository;
import com.lalthanpuia.addma1.entity.Incident;
import com.lalthanpuia.addma1.entity.Relief;

@Service
public class RequestReliefMaterialServiceImpl implements RequestReliefMaterialService {

	private RequestReliefMaterialRepository requestReliefMaterialRepository;
	
	@Autowired
	public RequestReliefMaterialServiceImpl(RequestReliefMaterialRepository theRequestReliefMaterialRepository) {
		requestReliefMaterialRepository = theRequestReliefMaterialRepository;
	}
	
	//@Override
	public List<Relief> findAll() {

		return requestReliefMaterialRepository.findAll();
	}

	//@Override
	public Relief findById(int theId) {
		
		Optional<Relief> result = requestReliefMaterialRepository.findById(theId);
		
		Relief theRequestReliefMaterialEntity = null;
		
		if (result.isPresent())
			theRequestReliefMaterialEntity = result.get();
		else 
			throw new RuntimeException ("Did not find employee id-" + theId);
		
		return theRequestReliefMaterialEntity;
	}

//	@Override
	public void save(Relief theRequestReliefMaterialEntity) {
		requestReliefMaterialRepository.save(theRequestReliefMaterialEntity);
	}

	//@Override
	public void deleteById(int theId) {
		requestReliefMaterialRepository.deleteById(theId);		
	}

	public List<Relief> findByPhone(String thePhone) {
		List<Relief> result = requestReliefMaterialRepository.findByPhone(thePhone);
		
		//List<Incident> theIncidentResult= null;
		
		
		System.out.println(result.get(0));	
		
		return result;
	}

	public Relief findFirst1ByOrderBySerialNumberDesc() {
		Relief result = requestReliefMaterialRepository.findFirst1ByOrderBySerialNumberDesc();
		
		return result;
	}

	public List<Relief> findByOfficerId(String theOfficerId) {
		List<Relief> result = requestReliefMaterialRepository.findByOfficerId(theOfficerId);		
		
		return result;
	}

	public List<Relief> findByOfficerContact(String theOfficerContact) {

		List<Relief> result = requestReliefMaterialRepository.findByOfficerContact(theOfficerContact);		

		return result;
	}

	public Relief findBySerialNumber(int theSerialNumber) {
		Relief result = requestReliefMaterialRepository.findBySerialNumber(theSerialNumber);
		
		return result;
	}

}
