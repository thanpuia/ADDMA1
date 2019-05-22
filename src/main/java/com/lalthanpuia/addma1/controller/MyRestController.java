package com.lalthanpuia.addma1.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lalthanpuia.addma1.entity.Incident;
import com.lalthanpuia.addma1.entity.Officer;
import com.lalthanpuia.addma1.entity.Relief;
import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.service.NotificationService;
import com.lalthanpuia.addma1.service.ReportIncidentService;
import com.lalthanpuia.addma1.service.RequestReliefMaterialService;
import com.lalthanpuia.addma1.service.UserEntityService;
import com.lalthanpuia.addma1.service.UserNotificationService;
import com.lalthanpuia.addma1.service.ZonalOfficerService;

@RestController
public class MyRestController {
	
	private ReportIncidentService reportIncidentService;
	private RequestReliefMaterialService requestReliefMaterialService;
	private UserEntityService userEntityService;
	private ZonalOfficerService zonalOfficerService;
	private NotificationService notificationRequestReliefService;
	private UserNotificationService userNotificationService;
	
	public User mUser  = new User();
	
	@Autowired
	private JavaMailSender sender;
	
	public MyRestController(ReportIncidentService theReportIncidentService, RequestReliefMaterialService theRequestReliefMaterialService, UserEntityService theUserEntityService, ZonalOfficerService theZonalOfficerService, NotificationService theNotificationRequestReliefService, UserNotificationService theUserNotificationService) {
		
		this.reportIncidentService = theReportIncidentService;
		this.requestReliefMaterialService = theRequestReliefMaterialService;

		this.userEntityService = theUserEntityService;
		this.zonalOfficerService = theZonalOfficerService; 
		this.notificationRequestReliefService = theNotificationRequestReliefService;
		this.userNotificationService = theUserNotificationService;
		//mUser = new User();
	}
	
	@RequestMapping("/test")
	public List<User> testing() {
		
		List<User> theUser = userEntityService.findAll();
		return theUser;
	}
	
	//REGISTER API HANDLER
	@PostMapping("/posttest")
	public String test(@RequestBody User newUser) {
		
		userEntityService.save(newUser);
		
		
		return "save";
	}
	
	//HANDLE NEW REGISTRATION
		@PostMapping("/test/newUser")
		public String UserInsert(@RequestBody User newUser) {
			
			String username= newUser.getUsername();
			String phone = newUser.getPhoneNo();
			
			System.out.println("username: "+ username);
			System.out.println("phone: "+phone);
			
			try{
				//CHECK THE AVAILABILITY OF USERNAME BY CALLING IT
				User mUser1 = userEntityService.findByUsername(username);
				
				System.out.println("mUser Username: "+mUser1.getUsername());

				
				//CHECK THE AVAILABILTIY OF PHONE NUMBER BY CALLING IT;
		///		User mUser2 = userEntityService.findByPhoneNo(phone);
		  
//				if(mUser1 != null) {
//					System.out.println("Username all ready present"+mUser1);
//					if(mUser2 != null) {	}
//					
//				}else {
//					System.out.println("unique name"+mUser1);
//					userEntityService.save(newUser);
//				}	
				System.out.println("Do nothing , they already present");
			
			}catch(Exception e) {
				System.out.println("error: "+e);

				System.out.println("Insert new User!");
				userEntityService.save(newUser);
				
			}	
			return "save";
		}

	//REQUEST RELIEF API
	@PostMapping("/post/relief")
	public String requestReliefInsert(@RequestBody Relief newRelief) {
		
		requestReliefMaterialService.save(newRelief);
		System.out.println("Checking post relief: "+newRelief.getMaterial());
		
		//sent to mail
		reliefSendMail(newRelief);
		return "save";
	}
	
	//INCIDENT REPORT API
	@PostMapping("/post/incident")
	public String incidentReportInsert(@RequestBody Incident newIncident) {
		
		reportIncidentService.save(newIncident);
		
		//sent to mail
		incidentSendMail(newIncident);
		return "save";
	}
	
	// AUTHENTICATION LOGIN
	@GetMapping("/test/{username}/{password}")
	public User login(@PathVariable String username, @PathVariable String password) {

		//mUser = new User();

//		System.out.println("Username: "+ username);
//		
//		//the noop cannot be added in the client side so it must be here
//		password =password;
//		System.out.println("Password: "+ password);
//
//		
//		//try {
//			//User mUser = new User();
//			System.out.println("1");
//			mUser = userEntityService.findByUsername(username);
//			
//			System.out.println("2"+ mUser.getUsername());
//
//			String mPassword = mUser.getPassword();	
//			System.out.println("Username: "+ username);
//
//			System.out.println("Real userpassword: "+mPassword);
////			if(mPassword.equals(password)) {
////				System.out.println("3");
////
////			}else {
////				mUser = null;
////				System.out.println("INside if Else: ");
////				return mUser;
////			}
////			System.out.println("4");
////
////				
////		}catch(Exception e) {
////			System.out.println("User inside catch:"+mUser.getUsername());
////			System.out.println("Error: "+e);
////			System.out.println("5");
////
////			mUser = null;
////			return mUser;
////		}
//		System.out.println("6");
//
//		System.out.println("User otsi:"+mUser.getUsername());
//	    return mUser;
		
		System.out.println("Username: "+username);
		System.out.println("Password: "+password);
		password = "{noop}"+password;

		
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

	
	// GET THE ZONAL OFFICER
	@GetMapping("/test/{locality}")
	public Officer zoneOfficer(@PathVariable String locality) {
		
		Officer mOfficer = new Officer();
		
		try {
			mOfficer = zonalOfficerService.findByOfficerLocality(locality);
		}catch(Exception e) {
			mOfficer = null;
			return mOfficer;
		}
		return mOfficer;
	}
	
	
	//FOR REST RELIEF SENDING MAIL
		@RequestMapping ("/rest/relief/sendMail")
		public String reliefSendMail(Relief mRequestReliefMaterialEntity) {
			
			//GET THE USER DATA
			//prepopu(null);
			
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			String subject = 
					"Request Relief for "+ mRequestReliefMaterialEntity.getMaterial()+
					" from " + mRequestReliefMaterialEntity.getUsername();

			String messageBody = 
					"District: " + mRequestReliefMaterialEntity.getDistrict() +"\n "+
					"Landmark: " + mRequestReliefMaterialEntity.getLandmarks()+"\n "+
					"Locality: " + mRequestReliefMaterialEntity.getLocality()+"\n "+
					"Material: " + mRequestReliefMaterialEntity.getMaterial()+"\n "+
					"Quantity: " + mRequestReliefMaterialEntity.getQuantity()+"\n "+
					"Name: " + mRequestReliefMaterialEntity.getUsername()+"\n "+
					"Phone: " + mRequestReliefMaterialEntity.getPhone()+"\n "+
					"Zonal Officer Contact: " + mRequestReliefMaterialEntity.getOfficerContact()+"\n "+
					"Zonal Officer Name: " + mRequestReliefMaterialEntity.getOfficerName()+"\n ";
	
			
	        try {
	            helper.setTo("thanpuia46@gmail.com");
	            helper.setText(messageBody);
	            helper.setSubject(subject);	
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            return "Error while sending mail ..";
	        }
	        sender.send(message);
	        return "Mail Sent Success!";
		}
		
		
		//FOR incident REST SENDING MAIL
			@RequestMapping ("/rest/report/sendMail")
			public String incidentSendMail(Incident mReportIncidentEntity) {
				
				//GET THE USER DATA
				//prepopu(null);
				
				MimeMessage message = sender.createMimeMessage();
				MimeMessageHelper helper=new MimeMessageHelper(message);
				
				String subject = 
						"Report Incident for "+ mReportIncidentEntity.getDisasterType()+
						" from " + mReportIncidentEntity.getUsername();
				System.out.println("Report incident for" + mReportIncidentEntity.getDisasterType());

				String messageBody = 
						"TYPE: Report Incident" +"\n" +
						"Disaster Type: " + mReportIncidentEntity.getDisasterType() +"\n "+

						"Details: " + mReportIncidentEntity.getDisastersDetails() +"\n "+

						"District: " 				+ mReportIncidentEntity.getDistrict() +"\n "+
						"Landmark: " 				+ mReportIncidentEntity.getLandmarks()+"\n "+
						"Locality: " 				+ mReportIncidentEntity.getLocality()+"\n "+
						"Name: " 					+ mReportIncidentEntity.getUsername()+"\n "+
						"Phone: " 					+ mReportIncidentEntity.getPhone()+"\n "+
						"Zonal Officer Contact: " 	+ mReportIncidentEntity.getOfficerContact()+"\n "+
						"Zonal Officer Name: "	 	+ mReportIncidentEntity.getOfficerName() +"\n ";

		        try {
		            helper.setTo("thanpuia46@gmail.com");
		            helper.setText(messageBody);
		            helper.setSubject(subject);
		        } catch (MessagingException e) {
		            e.printStackTrace();
		            return "Error while sending mail ..";
		        }
		        sender.send(message);
		        return "Mail Sent Success!";
			}
			
			
		
}
