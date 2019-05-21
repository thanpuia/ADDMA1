package com.lalthanpuia.addma1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lalthanpuia.addma1.entity.Relief;
import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.entity.UserNotification;
import com.lalthanpuia.addma1.entity.Officer;
import com.lalthanpuia.addma1.service.NotificationService;
import com.lalthanpuia.addma1.service.RequestReliefMaterialService;
import com.lalthanpuia.addma1.service.UserEntityService;
import com.lalthanpuia.addma1.service.UserNotificationService;
import com.lalthanpuia.addma1.service.ZonalOfficerService;

@Controller
@RequestMapping("/request")
public class RequestReliefMaterialController {

	private RequestReliefMaterialService requestReliefMaterialService;
	private UserEntityService userEntityService;
	private ZonalOfficerService zonalOfficerService;
	private NotificationService notificationRequestReliefService;
	private UserNotificationService userNotificationService;
	
	@Autowired
	private JavaMailSender sender;

	public RequestReliefMaterialController (RequestReliefMaterialService theRequestReliefMaterialService, UserEntityService theUserEntityService, ZonalOfficerService theZonalOfficerService, NotificationService theNotificationRequestReliefService, UserNotificationService theUserNotificationService) {
		
		this.requestReliefMaterialService = theRequestReliefMaterialService;
		this.userEntityService = theUserEntityService;
		this.zonalOfficerService = theZonalOfficerService; 
		this.notificationRequestReliefService = theNotificationRequestReliefService;
		this.userNotificationService = theUserNotificationService;
	}
	
	//REQUEST RELIEF HANDLER 1
	@GetMapping("requestRelief")
	public String requestReliefMaterial(Model theModel) {
		
		Relief theRequestReliefMaterialEntity = new Relief();
		
		theModel.addAttribute("requestReliefMaterial", theRequestReliefMaterialEntity);		
		
		
		return "requestReliefMaterialForm";
	}
	
	//REPORT INCIDENT HANDLER 2
	@PostMapping(value="/saveRequestRelief")
	public String saveRequestRelief (@ModelAttribute("requestReliefMaterial") Relief theRequestReliefMaterialEntity, Model theModel) {
				
		//theRequestReliefMaterialEntity.setDistrict ("districtTesting");
		//theRequestReliefMaterialEntity.setLocality(myUserEntity.getLocality());
		
		//1. GET THE USER DATA
			//1.1 TRYING TO GET THE USER NAME
				String username  ="";
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				if(principal instanceof UserDetails) 
					 username = ((UserDetails)principal).getUsername();
				else username = principal.toString();
				
				theModel.addAttribute("username", username);
				System.out.println(""+username);
				
				//UserEntity myUserEntity = getUserDetails(1);
				//System.out.println(myUserEntity.getEmail());
				
				// 1.2 GET THE USER DISTRICT SO THAT WE CAN FETCH THE ZONAL OFFICER
				String myDistrict = theRequestReliefMaterialEntity.getDistrict();
				String myLocality = theRequestReliefMaterialEntity.getLocality();

				//	System.out.print("this is the dristrict: "+myDistrict);
				
				/*
				HERER WE TAKE THE VALUE OF Locality from reportIncidentEntity.. the value of the Locality is similar to
				the zone id in the zonal officer table .. so using the String myLocality we can get the current zonalOfficer below
				*/
				//Officer currentZonalOfficer = zonalOfficerService.findByDistrict(myDistrict);
				Officer currentZonalOfficer = zonalOfficerService.findByOfficerZone(myLocality);
				System.out.print("this is the Zonal Officer: "+currentZonalOfficer.getOfficerName());
				
				
			//2. GET THE USERDETAILS	
				
				//2.1 GET THE USER DETAILS FROM THE USER ENTITY TABLE
				User currentUserEntity = getUserDetails(username);
				
				int tempSerialNumber = currentUserEntity.getSerialNumber();
				String mSerialNumber =""+tempSerialNumber;
				String mUsername = currentUserEntity.getUsername();
				String mPhone = currentUserEntity.getPhoneNo();
				
				System.out.print("Username "+mPhone);

				//2.2 GET THE USERDETAILS FROM THE SMART PHONE
				
			
				
				//2.3 GET THE ZONAL OFFICER DETAILS FROM THE ZONALOFFICER TABLE
			
				String officerContact = currentZonalOfficer.getOfficerContact();
				String officerDesignation = currentZonalOfficer.getOfficerDesignation();
				String offierDistrict = currentZonalOfficer.getOfficerDistrict();
				String officerEmail = currentZonalOfficer.getOfficerEmail();
				String officerZone = currentZonalOfficer.getOfficerZone();
				String officerName = currentZonalOfficer.getOfficerName();
				String officerId = currentZonalOfficer.getOfficerId();
				
				//int zoneId = currentZonalOfficer.getZoneId();
				//String zoneIdStr = ""+zoneId;

			//3. PUT THE USERDETAILS 
				
				//3.1 PUT THE USERDETAILS FROM THE USERENTITY
				theRequestReliefMaterialEntity.setUsername(mUsername);
				theRequestReliefMaterialEntity.setPhone(mPhone);
				
				//3.2. PUT THE SYSTEM DATA INTO THE ENTITY. 
				//	THIS SHOULD BE TAKEN FROM THE SMART PHONE AT THE TIME OF REPORT, BUT 
				//	FOR NOW WE PUT DUMMY VALUE HOLDER
				theRequestReliefMaterialEntity.setDistrict	(myDistrict);
				theRequestReliefMaterialEntity.setLat		("dummy lat");
				theRequestReliefMaterialEntity.setLng		("dummy lng");
				theRequestReliefMaterialEntity.setRequestOn	("dummy requestOn");
				//theRequestReliefMaterialEntity.setStatus	("dummy status");
				
				//3.3. PUT THE ZONAL OFFICER DETAILS FROM THE ZONAL OFFICER TABLE.
				// THE ZONAL OFFICER TABLE HAS NOT BEEM CREATED SO FILLED WITH DUMMY VALUE
				theRequestReliefMaterialEntity.setOfficerContact(officerContact);
				theRequestReliefMaterialEntity.setOfficerId(officerId);
				theRequestReliefMaterialEntity.setOfficerName(officerName);
//				//theRequestReliefMaterialEntity.setZoneId(zoneIdStr);
				theRequestReliefMaterialEntity.setOfficerZone(officerZone);
				
				System.out.print(officerContact);
		
		//GET THE CURRENT TIME
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
				theUserNotification.setOfficerName(officerName);
				theUserNotification.setOfficerContact(officerContact);
				
		
		//SAVE THE USER DATA
		requestReliefMaterialService.save(theRequestReliefMaterialEntity);
		userNotificationService.save(theUserNotification);
		
		//System.out.print("Officer contact"+theRequestReliefMaterialEntity.getZonalOfficerContact());
		
		
		//SEND THE REQUEST DETAILS TO THE MAIL
		sendMail(theRequestReliefMaterialEntity);
		return "mail-success";
		//return "redirect:/showAll";
		
	}

//	@GetMapping("/prepopulater")
//	public String prepopu(Model theModel) {
//		
////		RequestReliefMaterialEntity theRequestReliefMaterialEntity = new RequestReliefMaterialEntity();
//		
////		theModel.addAttribute("requestReliefMaterial", theRequestReliefMaterialEntity);
//		
//		//TRYING TO GET THE USER NAME
//		String username  ="";
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if(principal instanceof UserDetails) 
//			 username = ((UserDetails)principal).getUsername();
//		else username = principal.toString();
//		
//		theModel.addAttribute("username", username);
//		System.out.println(""+username);
//		
//		//UserEntity myUserEntity = getUserDetails(1);
//		//System.out.println(myUserEntity.getEmail());
//		getUserDetails(username);
//		return "home";
//	}
	
	//GET USER DETAILS
	public User getUserDetails(String theUsername) {
		
		User currentUserEntity = userEntityService.findByUsername(theUsername);
		
		//System.out.println(currentUserEntity.getPhoneNo());
		
		return 	currentUserEntity;
	}
	
	//GET ZONE OFFICER DETAILS
	public Officer getCurrentZonalOfficer(int zoneid) {
		
		Officer currentZonalOfficer = null;//zonalOfficerService.findById(zoneid);
		return currentZonalOfficer;
	}
	
	//FOR SENDING MAIL
	@RequestMapping ("/sendMail")
	public String sendMail(Relief mRequestReliefMaterialEntity) {
		
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
;
		
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
















