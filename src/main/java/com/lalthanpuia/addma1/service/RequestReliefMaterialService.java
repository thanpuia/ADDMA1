package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.RequestReliefMaterialEntity;

public interface RequestReliefMaterialService {

	public List<RequestReliefMaterialEntity> findAll();
	
	public RequestReliefMaterialEntity findById (int theId);
	
	public void save (RequestReliefMaterialEntity theRequestReliefMaterialEntity);
	
	public void deleteById (int theId);
}
