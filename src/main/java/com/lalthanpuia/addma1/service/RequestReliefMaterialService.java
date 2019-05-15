package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.Relief;

public interface RequestReliefMaterialService {

	public List<Relief> findAll();
	
	public Relief findById (int theId);
	
	public void save (Relief theRequestReliefMaterialEntity);
	
	public void deleteById (int theId);
}
