package com.lalthanpuia.addma1.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lalthanpuia.addma1.dao.RequestReliefMaterialRepository;
import com.lalthanpuia.addma1.entity.ReportIncidentEntity;
import com.lalthanpuia.addma1.entity.RequestReliefMaterialEntity;

@Controller
@RequestMapping("/request")
public class RequestReliefMaterialController {

	private RequestReliefMaterialRepository requestReliefMaterialRepository;
	
	public RequestReliefMaterialController (RequestReliefMaterialRepository theRequestReliefMaterialRepository) {
		
		requestReliefMaterialRepository = theRequestReliefMaterialRepository;
	}
	
	//REQUEST RELIEF HANDLER 1
	@GetMapping("requestRelief")
	public String requestReliefMaterial(Model theModel) {
		
		RequestReliefMaterialEntity theRequestReliefMaterialEntity = new RequestReliefMaterialEntity();
		theModel.addAttribute("requestReliefMaterial", theRequestReliefMaterialEntity);
		return "requestReliefMaterialForm";
	}
	
	//REPORT INCIDENT HANDLER 2
	@PostMapping(value="/saveRequestRelief")
	public String saveRequestRelief (@Valid RequestReliefMaterialEntity theRequestReliefMaterialEntity, BindingResult bindingResult, Model theModel) {
		
		if (bindingResult.hasErrors()) {
			return "error";
		}
		else {
			requestReliefMaterialRepository.save(theRequestReliefMaterialEntity);
			return "home";
		}
		
	}
}
