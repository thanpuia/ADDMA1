package com.lalthanpuia.addma1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lalthanpuia.addma1.entity.Incident;
import com.lalthanpuia.addma1.entity.Notification;
import com.lalthanpuia.addma1.entity.Relief;
import com.lalthanpuia.addma1.service.NotificationService;
import com.lalthanpuia.addma1.service.ReportIncidentService;
import com.lalthanpuia.addma1.service.RequestReliefMaterialService;
import com.lalthanpuia.addma1.service.UserEntityService;
import com.lalthanpuia.addma1.service.UserNotificationService;
import com.lalthanpuia.addma1.service.ZonalOfficerService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private RequestReliefMaterialService requestReliefMaterialService;
	private UserEntityService userEntityService;
	private ZonalOfficerService zonalOfficerService;
	private NotificationService notificationService;
	private ReportIncidentService reportIncidentService;
	//private NotificationService notificationService;
	
	@Autowired
	private JavaMailSender sender;
	
	public AdminController (RequestReliefMaterialService theRequestReliefMaterialService,ReportIncidentService theReportIncidentService, UserEntityService theUserEntityService, ZonalOfficerService theZonalOfficerService, NotificationService theNotificationService, UserNotificationService theUserNotificationService) {
		
		this.requestReliefMaterialService = theRequestReliefMaterialService;
		this.userEntityService = theUserEntityService;
		this.zonalOfficerService = theZonalOfficerService; 
		this.notificationService = theNotificationService;
		this.reportIncidentService =theReportIncidentService;
		//this.userNotificationService = theUserNotificationService;
	}
	
	@GetMapping("/showNotify")
	public String showNotify(Model theModel) {
		
		List<Notification> theNotification = notificationService.findAll();
		
		theModel.addAttribute("allNotify", theNotification);
		return "admin/admin-notify";
	}
	
	@PostMapping("/update")
	public String update(@RequestParam("notificationId") int theId, Model theModel) {
		
		Notification theNotification = new Notification();
		theModel.addAttribute("myNotification", theNotification);
		System.out.println("id selectioed:" +theId);
		return "home";
	}
	
	///RELIEF
	@GetMapping("/showRelief")
	public String showRelief (Model theModel) {
		
		List<Relief> theRelief = requestReliefMaterialService.findAll();
		
		theModel.addAttribute("allRelief", theRelief);
		
		return "admin/admin-relief";
	}
	
	@PostMapping("relief/update")
	public String reliefUpdate(@RequestParam("reliefId") int theId, Model theModel) {
		
		System.out.println("id selectioed:" +theId);
		
		Relief theRelief = requestReliefMaterialService.findById(theId);
		
		if(theRelief.getStatus().equals("resolve")) 
			theRelief.setStatus("not resolve");
		else
			theRelief.setStatus("resolve");

		
		requestReliefMaterialService.save(theRelief);
		
		return "redirect:/admin/showRelief";
	}
	
	
	//INCIDENT
	@GetMapping ("/showIncident")
	public String showIncident(Model theModel) {
		
		List<Incident> theIncident = reportIncidentService.findAll();
		theModel.addAttribute("allIncident",theIncident);
		
		return "admin/admin-incident";
	}
	@PostMapping("incident/update")
	public String incidentUpdate(@RequestParam("incidentId") int theId, Model theModel) {
		
		Incident theIncident = reportIncidentService.findById(theId);
		
		if(theIncident.getStatus().equals("resolve"))
			theIncident.setStatus("not resolve");
		else
			theIncident.setStatus("resolve");
		
		reportIncidentService.save(theIncident);
		
		return "redirect:/admin/showIncident";
	}
}
