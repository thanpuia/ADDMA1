package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalthanpuia.addma1.dao.ReportIncidentRepository;
import com.lalthanpuia.addma1.entity.ReportIncidentEntity;

@Service
public class ReportIncidentServiceImpl implements ReportIncidentService {

	private ReportIncidentRepository reportIncidentRepository;
	
	@Autowired
	public ReportIncidentServiceImpl (ReportIncidentRepository theReportIncidentRepository) {
		reportIncidentRepository = theReportIncidentRepository;
	}
	
	@Override
	public List<ReportIncidentEntity> findAll() {
		return reportIncidentRepository.findAll();
	}

	@Override
	public ReportIncidentEntity findById(int theId) {
Optional<ReportIncidentEntity> result = reportIncidentRepository.findById(theId);
		
ReportIncidentEntity theReportIncidentEntity = null;
		if(result.isPresent()) 
			theReportIncidentEntity=result.get();
		else
			throw new RuntimeException("Did not find employee id - "+ theId);
		
		return theReportIncidentEntity;
	}

	@Override
	public void save(ReportIncidentEntity theReportIncidentEntity) {

		reportIncidentRepository.save(theReportIncidentEntity);
	}

	@Override
	public void deleteById(int theId) {
		
		reportIncidentRepository.deleteById(theId);

	}

}
