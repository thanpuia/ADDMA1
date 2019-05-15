package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.ReportIncidentRepository;
import com.lalthanpuia.addma1.entity.Incident;

@Service
public class ReportIncidentServiceImpl implements ReportIncidentService {

	private ReportIncidentRepository reportIncidentRepository;
	
	@Autowired
	public ReportIncidentServiceImpl (ReportIncidentRepository theReportIncidentRepository) {
		reportIncidentRepository = theReportIncidentRepository;
	}
	
	@Override
	public List<Incident> findAll() {
		return reportIncidentRepository.findAll();
	}

	@Override
	public Incident findById(int theId) {
Optional<Incident> result = reportIncidentRepository.findById(theId);
		
Incident theReportIncidentEntity = null;
		if(result.isPresent()) 
			theReportIncidentEntity=result.get();
		else
			throw new RuntimeException("Did not find employee id - "+ theId);
		
		return theReportIncidentEntity;
	}

	@Override
	public void save(Incident theReportIncidentEntity) {

		reportIncidentRepository.save(theReportIncidentEntity);
	}

	@Override
	public void deleteById(int theId) {
		
		reportIncidentRepository.deleteById(theId);

	}

}
