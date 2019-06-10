package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.Incident;

public interface ReportIncidentService {

	public List<Incident> findAll();
	
	public Incident findById (int theId);
	
	public void save (Incident theReportIncidentEntity);
	
	public void deleteById (int theId);
	
	public List<Incident> findByPhone(String thePhone);
	
	public Incident findFirst1ByOrderBySerialNumberDesc();
	
	public List<Incident> findByOfficerId(String theOfficerId);
	public List<Incident> findByOfficerContact(String theOfficerContact);
	
	//public Incident findBySerialNumber(int theSerialNumber, String status);
	public Incident findBySerialNumber(int theSerialNumber);

}
