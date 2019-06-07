package com.lalthanpuia.addma1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lalthanpuia.addma1.entity.Incident;

public interface ReportIncidentRepository extends JpaRepository<Incident, Integer> {
	
	public List<Incident> findByPhone(String thePhone);

	public Incident findFirst1ByOrderBySerialNumberDesc();
	
	public List<Incident> findByOfficerId(String theOfficerId);
	public List<Incident> findByOfficerContact(String theOfficerContact);

}
