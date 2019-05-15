package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.RequestReliefMaterialRepository;
import com.lalthanpuia.addma1.entity.Relief;

@Service
public class RequestReliefMaterialServiceImpl implements RequestReliefMaterialService {

	private RequestReliefMaterialRepository requestReliefMaterialRepository;
	
	@Autowired
	public RequestReliefMaterialServiceImpl(RequestReliefMaterialRepository theRequestReliefMaterialRepository) {
		requestReliefMaterialRepository = theRequestReliefMaterialRepository;
	}
	
	@Override
	public List<Relief> findAll() {

		return requestReliefMaterialRepository.findAll();
	}

	@Override
	public Relief findById(int theId) {
		
		Optional<Relief> result = requestReliefMaterialRepository.findById(theId);
		
		Relief theRequestReliefMaterialEntity = null;
		
		if (result.isPresent())
			theRequestReliefMaterialEntity = result.get();
		else 
			throw new RuntimeException ("Did not find employee id-" + theId);
		
		return theRequestReliefMaterialEntity;
	}

	@Override
	public void save(Relief theRequestReliefMaterialEntity) {
		requestReliefMaterialRepository.save(theRequestReliefMaterialEntity);
	}

	@Override
	public void deleteById(int theId) {
		requestReliefMaterialRepository.deleteById(theId);		
	}

}
