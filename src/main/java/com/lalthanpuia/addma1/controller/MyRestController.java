package com.lalthanpuia.addma1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.service.NotificationService;
import com.lalthanpuia.addma1.service.ReportIncidentService;
import com.lalthanpuia.addma1.service.UserEntityService;
import com.lalthanpuia.addma1.service.UserNotificationService;
import com.lalthanpuia.addma1.service.ZonalOfficerService;

@RestController
public class MyRestController {
	
	private ReportIncidentService reportIncidentService;
	private UserEntityService userEntityService;
	private ZonalOfficerService zonalOfficerService;
	private NotificationService notificationRequestReliefService;
	private UserNotificationService userNotificationService;
	
	public MyRestController(ReportIncidentService theReportIncidentService, UserEntityService theUserEntityService, ZonalOfficerService theZonalOfficerService, NotificationService theNotificationRequestReliefService, UserNotificationService theUserNotificationService) {
		
		this.reportIncidentService = theReportIncidentService;
		this.userEntityService = theUserEntityService;
		this.zonalOfficerService = theZonalOfficerService; 
		this.notificationRequestReliefService = theNotificationRequestReliefService;
		this.userNotificationService = theUserNotificationService;
	}
	
	@RequestMapping("/test")
	public List<User> testing() {
		
		List<User> theUser = userEntityService.findAll();
		
		return theUser;
	}
	
	@PostMapping("/posttest")
	public String UserInsert(@RequestBody User newUser) {
		
		userEntityService.save(newUser);
		
		return "save";
	}

	//checking loging
	@GetMapping("/test/{username}/{password}")
	public User login(@PathVariable String username, @PathVariable String password) {

		System.out.println("Username: "+username);
		System.out.println("Password: "+password);

		
		User mUser = new User();
		try {
			mUser = userEntityService.findByUsername(username);
			String mPassword = mUser.getPassword();	
			System.out.println("Real userpassowr: "+mPassword);
			if(mPassword.equals(password)) {
			}else {
				mUser = null;
				System.out.println("INside if Else: ");
				return mUser;
			}
				
				
		}catch(Exception e) {
			System.out.println("User inside catch:"+mUser.getUsername());
				
			mUser = null;
			return mUser;
		}
			
		System.out.println("User otsi:"+mUser.getUsername());
	    return mUser;
	  }
}
