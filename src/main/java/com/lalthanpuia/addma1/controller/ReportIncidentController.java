package com.lalthanpuia.addma1.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lalthanpuia.addma1.dao.ReportIncidentRepository;
import com.lalthanpuia.addma1.dao.UserRepository;
import com.lalthanpuia.addma1.entity.ReportIncidentEntity;
import com.lalthanpuia.addma1.entity.UserEntity;
						
@Controller				
@RequestMapping("/report")
public class ReportIncidentController {

	private ReportIncidentRepository reportIncidentRepository;
	private UserRepository userRepository;

	public ReportIncidentController(ReportIncidentRepository theReportIncidentRepository, UserRepository theUserRepository) {
		
		this.reportIncidentRepository = theReportIncidentRepository;
		this.userRepository = theUserRepository;
	}
	
	//REPORT INCIDENT HANDLER 1
	@GetMapping("/reportIncident")
	public String reportincidented(Model theModel) {
		
		UserEntity theUserEntity					 = new UserEntity();
		ReportIncidentEntity theReportIncidentEntity = new ReportIncidentEntity();
		theModel.addAttribute("reportIncidentEntity", theReportIncidentEntity);
		return "/reportIncident";
		//return "/signUp";
	}
	
//	@RequestMapping(value="/testLink", method=RequestMethod.GET)
//	public String test() {
//		return "/signUp";
//	}
	//REPORT INCIDENT HANDLER 2
	@PostMapping(value="/saveReportIncident")//),method = RequestMethod.POST)
	public String saveReport (@Valid ReportIncidentEntity theReportIncidentEntity, BindingResult bindingResult, Model theModel) {
		
		if(bindingResult.hasErrors()) {
			return "error";
		}else {
			reportIncidentRepository.save(theReportIncidentEntity);
			return "home";
		}
	}
}