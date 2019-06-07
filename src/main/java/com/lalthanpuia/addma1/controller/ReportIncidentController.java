package com.lalthanpuia.addma1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lalthanpuia.addma1.entity.Incident;
import com.lalthanpuia.addma1.entity.Relief;
import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.entity.UserNotification;
import com.lalthanpuia.addma1.entity.Officer;
import com.lalthanpuia.addma1.service.NotificationService;
import com.lalthanpuia.addma1.service.ReportIncidentService;
import com.lalthanpuia.addma1.service.UserEntityService;
import com.lalthanpuia.addma1.service.UserNotificationService;
import com.lalthanpuia.addma1.service.ZonalOfficerService;
						
@Controller				
@RequestMapping("/report")
public class ReportIncidentController {

	private ReportIncidentService reportIncidentService;
	private UserEntityService userEntityService;
	private ZonalOfficerService zonalOfficerService;
	private NotificationService notificationRequestReliefService;
	private UserNotificationService userNotificationService;

	@Autowired
	private JavaMailSender sender;
	
	public ReportIncidentController(ReportIncidentService theReportIncidentService, UserEntityService theUserEntityService, ZonalOfficerService theZonalOfficerService, NotificationService theNotificationRequestReliefService, UserNotificationService theUserNotificationService) {
		
		this.reportIncidentService = theReportIncidentService;
		this.userEntityService = theUserEntityService;
		this.zonalOfficerService = theZonalOfficerService; 
		this.notificationRequestReliefService = theNotificationRequestReliefService;
		this.userNotificationService = theUserNotificationService;
	}
		
	
	//REPORT INCIDENT HANDLER 1
	@GetMapping("/reportIncident")
	public String reportincidented(Model theModel) {
		
		User theUserEntity					= new User();
		Incident theReportIncidentEntity 	= new Incident();
		theModel.addAttribute("reportIncidentEntity", theReportIncidentEntity);
		return "/reportIncident";
		//return "/signUp";
	}
	
	//REPORT INCIDENT HANDLER 2
	@PostMapping(value="/saveReportIncident")//),method = RequestMethod.POST)
	public String saveReport (@Valid Incident theReportIncidentEntity, BindingResult bindingResult, Model theModel) {
		
		//1. GET THE USER DATA
			//1.1 GET THE USER NAME
			String username = "";
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(principal instanceof UserDetails)
				username = ((UserDetails)principal).getUsername();
			else username = principal.toString();
			
			theModel.addAttribute("username", username);
			
			System.out.println("Username from controller:"+username);
			
			//1.2 GET THE DISTRICT SO THAT WE CAN FETCH THE ZONAL OFFICER
			String myDistrict = theReportIncidentEntity.getDistrict();
			String myLocality = theReportIncidentEntity.getLocality();
			System.out.println("current district:" + myDistrict);
			System.out.println("current locality:" + myLocality);
			
			/*
			HERE WE TAKE THE VALUE OF Locality from reportIncidentEntity.. the value of the Locality is similar to
			the zone id in the zonal officer table .. so using the String myLocality we can get the current zonalOfficer below
			*/
			//Officer currentZonalOfficer = zonalOfficerService.findByDistrict(myDistrict);
			//Officer currentZonalOfficer = zonalOfficerService.findByOfficerZone(myLocality);

			//System.out.println("This is the zonal officer:" + currentZonalOfficer);
			
			//2. GET THE USER DETAILS
			
			//2.1 GET THE USER DETAILS FORM THE USER ENTITY TABLE
			User currentUserEntity = getUserDetails(username);
			
			int tempSerialNumber = currentUserEntity.getSerialNumber();
			String mSerialNumber = "" + tempSerialNumber;
			String mUsername = currentUserEntity.getUsername();
			String mPhone = currentUserEntity.getPhoneNo();
			
			//2.2 GET THE USER DETAILS FROM THE SMART PHONE
			
			//2.3 GET THE ZONAL OFFICER DETAILS FROM THE ZONAL-OFFICER TABLE
			/*
			String officerContact 		= currentZonalOfficer.getOfficerContact();
			String officerDesignation 	= currentZonalOfficer.getOfficerDesignation();
			String officerDistrict		= currentZonalOfficer.getOfficerDistrict();
			String officerEmail			= currentZonalOfficer.getOfficerEmail();
			String officerZone			= currentZonalOfficer.getOfficerZone();
			String officerName			= currentZonalOfficer.getOfficerName();
			String officerId			= currentZonalOfficer.getOfficerId();
			*/
		//3. PUT THE USER DETAILS
			
			//3.1 PUT THE USER DETAILS FROM THE USER ENTITY
			theReportIncidentEntity.setUsername(mUsername);
			theReportIncidentEntity.setPhone(mPhone);
			
			
			//3.2 PUT THE SYSTEM DATA INTO THE ENTITY
			//	THIS SHOULD BE TAKEN FROM THE SMART PHONE AT THE TIME OF REPORT, BUT 
			//	FOR NOW WE PUT DUMMY VALUE HOLDER
			theReportIncidentEntity.setDistrict		(myDistrict);
			theReportIncidentEntity.setLng			("dummy lng");
			theReportIncidentEntity.setLat			("dummy lat");
			//theReportIncidentEntity.setStatus		("dummy status");
		
			//3.3. PUT THE ZONAL OFFICER DETAILS FROM THE ZONAL OFFICER TABLE.
			// THE ZONAL OFFICER TABLE HAS NOT BEEM CREATED SO FILLED WITH DUMMY VALUE
			//theReportIncidentEntity.setOfficerContact(officerContact);
			//theReportIncidentEntity.setOfficerId(officerId);
			//theReportIncidentEntity.setOfficerName(officerName);
//			//theReportIncidentEntity.setZoneId(zoneIdStr);
			//theReportIncidentEntity.setOfficerZone(officerZone);
		
		//4. GET THE CURRENT TIME	
			 Calendar cal = Calendar.getInstance();
		     Date date=cal.getTime();
		     DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		     String mCurrentTime=dateFormat.format(date);
		     System.out.println("Current time of the day using Calendar - 24 hour format: "+ mCurrentTime);
		 
		 	//GET THE DATA REQUIRED FOR USERNOTIFICATION TABLE
				UserNotification theUserNotification = new UserNotification();
				theUserNotification.setDistrict(myDistrict);
				theUserNotification.setMessage("");
				theUserNotification.setPhone(mPhone);
				theUserNotification.setSentTime(mCurrentTime);
				theUserNotification.setUserSerialNo(mSerialNumber);
				theUserNotification.setUsername(username);
				theUserNotification.setSentType("desktop");
				//theUserNotification.setOfficerName(officerName);
				//theUserNotification.setOfficerContact(officerContact);
		
				
				System.out.println("Right before save");

		
		if(bindingResult.hasErrors()) {
			return "error";
		}else {
			reportIncidentService.save(theReportIncidentEntity);
			
			Incident myLatestIncident = reportIncidentService.findFirst1ByOrderBySerialNumberDesc() ;
			
			System.out.println(myLatestIncident.getSerialNumber());
			//return "home";
		}
		

		//5. SENT REPORT DETAIL TO THE MAIL
				sendMail(theReportIncidentEntity);
				return "mail-success";
	}
	
	//GET THE USER DETAILS 
	public User getUserDetails(String theUsername) {
		System.out.println("Current user details (extracted) : ");

		User currentUserEntity = userEntityService.findByUsername(theUsername);
		System.out.println("Current user details (extracted) : "+currentUserEntity.getLocality());
		
		return currentUserEntity;
	}
	
	//FOR SENDING MAIL
		@RequestMapping ("/sendMail")
		public String sendMail(Incident mReportIncidentEntity) {
			
			//GET THE USER DATA
			//prepopu(null);
			
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			
			String subject = 
					"Report Incident for "+ mReportIncidentEntity.getDisasterType()+
					" from " + mReportIncidentEntity.getUsername();

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