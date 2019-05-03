package com.lalthanpuia.addma1.service;

import java.util.List;

import com.lalthanpuia.addma1.entity.ReportIncidentEntity;

public interface ReportIncidentService {

	public List<ReportIncidentEntity> findAll();
	
	public ReportIncidentEntity findById (int theId);
	
	public void save (ReportIncidentEntity theReportIncidentEntity);
	
	public void deleteById (int theId);
}
