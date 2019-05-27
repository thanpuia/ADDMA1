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
import org.springframework.web.client.RestTemplate;

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

	public User mUser = new User();

	@Autowired
	private JavaMailSender sender;

	public MyRestController(ReportIncidentService theReportIncidentService,
			RequestReliefMaterialService theRequestReliefMaterialService, UserEntityService theUserEntityService,
			ZonalOfficerService theZonalOfficerService, NotificationService theNotificationRequestReliefService,
			UserNotificationService theUserNotificationService) {

		this.reportIncidentService = theReportIncidentService;
		this.requestReliefMaterialService = theRequestReliefMaterialService;
		this.userEntityService = theUserEntityService;
		this.zonalOfficerService = theZonalOfficerService;
		this.notificationRequestReliefService = theNotificationRequestReliefService;
		this.userNotificationService = theUserNotificationService;
		// mUser = new User();
	}

	@RequestMapping("/test")
	public List<User> testing() {

		List<User> theUser = userEntityService.findAll();
		return theUser;
	}

	// REGISTER API HANDLER
	@PostMapping("/posttest")
	public String test(@RequestBody User newUser) {

		userEntityService.save(newUser);

		return "save";
	}

	// HANDLE NEW REGISTRATION
	@PostMapping("/test/newUser")
	public String UserInsert(@RequestBody User newUser) {

		String username = newUser.getUsername();
		String phone = newUser.getPhoneNo();

		System.out.println("username: " + username);
		System.out.println("phone: " + phone);

		try {
			// CHECK THE AVAILABILITY OF USERNAME BY CALLING IT
			User mUser1 = userEntityService.findByUsername(username);

			System.out.println("mUser Username: " + mUser1.getUsername());

			// CHECK THE AVAILABILTIY OF PHONE NUMBER BY CALLING IT;
			/// User mUser2 = userEntityService.findByPhoneNo(phone);

//				if(mUser1 != null) {
//					System.out.println("Username all ready present"+mUser1);
//					if(mUser2 != null) {	}
//					
//				}else {
//					System.out.println("unique name"+mUser1);
//					userEntityService.save(newUser);
//				}	
			System.out.println("Do nothing , they already present");

		} catch (Exception e) {
			System.out.println("error: " + e);

			System.out.println("Insert new User!");
			userEntityService.save(newUser);

		}
		return "save";
	}

	// REQUEST RELIEF API
	@PostMapping("/post/relief")
	public String requestReliefInsert(@RequestBody Relief newRelief) {

		// GET THE ZONAL OFFICER
		String locality = newRelief.getLocality();
		System.out.println("Locality: " + newRelief.getLocality());

		Officer currentZonalOfficer = zonalOfficerService.findByOfficerLocality(locality);
		// int zone = searchZonalOfficer(locality);

		System.out.println("lat: " + newRelief.getLat() + " lng: " + newRelief.getLng());

		// 2.3 GET THE ZONAL OFFICER DETAILS FROM THE ZONALOFFICER TABLE
		String officerContact = currentZonalOfficer.getOfficerContact();
		String officerDesignation = currentZonalOfficer.getOfficerDesignation();
		String offierDistrict = currentZonalOfficer.getOfficerDistrict();
		String officerEmail = currentZonalOfficer.getOfficerEmail();
		String officerZone = currentZonalOfficer.getOfficerZone();
		String officerName = currentZonalOfficer.getOfficerName();
		String officerId = currentZonalOfficer.getOfficerId();

		// 2.3. PUT THE ZONAL OFFICER DETAILS FROM THE ZONAL OFFICER TABLE.
		newRelief.setOfficerContact(officerContact);
		newRelief.setOfficerId(officerId);
		newRelief.setOfficerName(officerName);
		// theRequestReliefMaterialEntity.setZoneId(zoneIdStr);
		newRelief.setOfficerZone(officerZone);


		//sent sms to zonal officer
		smsRelief();
		
		// sent to mail
		reliefSendMail(newRelief);
		
	

		// Upload to REQUEST RELIEF TABLE
		requestReliefMaterialService.save(newRelief);
		System.out.println("Checking post relief: " + newRelief.getMaterial());

		return "save";
	}

	// INCIDENT REPORT API
	@PostMapping("/post/incident")
	public String incidentReportInsert(@RequestBody Incident newIncident) {

		// GET THE ZONAL OFFICER
		String locality = newIncident.getLocality();
		System.out.println("Locality: " + newIncident.getLocality());

		Officer currentZonalOfficer = zonalOfficerService.findByOfficerLocality(locality);

		// 2.3 GET THE ZONAL OFFICER DETAILS FROM THE ZONALOFFICER TABLE
		String officerContact = currentZonalOfficer.getOfficerContact();
		String officerDesignation = currentZonalOfficer.getOfficerDesignation();
		String offierDistrict = currentZonalOfficer.getOfficerDistrict();
		String officerEmail = currentZonalOfficer.getOfficerEmail();
		String officerZone = currentZonalOfficer.getOfficerZone();
		String officerName = currentZonalOfficer.getOfficerName();
		String officerId = currentZonalOfficer.getOfficerId();

		// 2.3. PUT THE ZONAL OFFICER DETAILS FROM THE ZONAL OFFICER TABLE.
		newIncident.setOfficerContact(officerContact);
		newIncident.setOfficerId(officerId);
		newIncident.setOfficerName(officerName);
		// newIncident.setZoneId(zoneIdStr);
		newIncident.setOfficerZone(officerZone);

		//sent sms to zonal officer
		smsIncident();
		
		// sent to mail
		incidentSendMail(newIncident);
		
	

		// UPLOAD TO INCIDENT REPORT TABLE
		reportIncidentService.save(newIncident);

		return "save";
	}

	// AUTHENTICATION LOGIN
	@GetMapping("/test/{phoneNo}/{password}")
	public User login(@PathVariable String phoneNo, @PathVariable String password) {

		// mUser = new User();

//		System.out.println("Username: "+ username);
//		
//		//the noop cannot be added in the client side so it must be here
//		password =password;
//		System.out.println("Password: "+ password);

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

		System.out.println("Phone Number: " + phoneNo);
		System.out.println("Password: " + password);
		password = "{noop}" + password;

		User mUser = new User();
		try {
			// mUser = userEntityService.findByUsername(phoneNo);
			mUser = userEntityService.findByPhoneNo(phoneNo);
			String mPassword = mUser.getPassword();
			System.out.println("Real userpassowr: " + mPassword + " " + mUser.getAltContactName());
			if (mPassword.equals(password)) {
			} else {
				mUser = null;
				System.out.println("INside if Else: ");
				return mUser;
			}
		} catch (Exception e) {
			System.out.println("User inside catch:" + mUser.getUsername());
			mUser = null;
			return mUser;
		}

		System.out.println("User otsi:" + mUser.getUsername());
		return mUser;
	}

	
	  // GET THE ZONAL OFFICER
	  @GetMapping("/get/{locality}") 
	  public Officer zoneOfficer(@PathVariable String locality) {
	 
		System.out.println(locality);
	  Officer mOfficer = new Officer();
	  
	 try {
		 mOfficer = zonalOfficerService.findByOfficerLocality(locality);
		  System.out.println("inside try"+mOfficer.getOfficerName());
		  return mOfficer;

	}catch(Exception e) { 
		  mOfficer = null; 
		  System.out.println("inside catch");

		  return mOfficer;
	} 
	 
	// return mOfficer;
	  
	  
	  }
	  
	 

	// FOR REST RELIEF SENDING MAIL
	@RequestMapping("/rest/relief/sendMail")
	public String reliefSendMail(Relief mRequestReliefMaterialEntity) {

		// GET THE USER DATA
		// prepopu(null);

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String subject = "Request Relief for " + mRequestReliefMaterialEntity.getMaterial() + " from "
				+ mRequestReliefMaterialEntity.getUsername();

		String messageBody = "District: " + mRequestReliefMaterialEntity.getDistrict() + "\n " + "Landmark: "
				+ mRequestReliefMaterialEntity.getLandmarks() + "\n " + "Locality: "
				+ mRequestReliefMaterialEntity.getLocality() + "\n " + "Material: "
				+ mRequestReliefMaterialEntity.getMaterial() + "\n " + "Quantity: "
				+ mRequestReliefMaterialEntity.getQuantity() + "\n " + "Name: "
				+ mRequestReliefMaterialEntity.getUsername() + "\n " + "Phone: "
				+ mRequestReliefMaterialEntity.getPhone() + "\n " + "Zonal Officer Contact: "
				+ mRequestReliefMaterialEntity.getOfficerContact() + "\n " + "Zonal Officer Name: "
				+ mRequestReliefMaterialEntity.getOfficerName() + "\n ";

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

	// FOR incident REST SENDING MAIL
	@RequestMapping("/rest/report/sendMail")
	public String incidentSendMail(Incident mReportIncidentEntity) {

		// GET THE USER DATA
		// prepopu(null);

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		String subject = "Report Incident for " + mReportIncidentEntity.getDisasterType() + " from "
				+ mReportIncidentEntity.getUsername();
		System.out.println("Report incident for" + mReportIncidentEntity.getDisasterType());

		String messageBody = "TYPE: Report Incident" + "\n" + "Disaster Type: "
				+ mReportIncidentEntity.getDisasterType() + "\n " +

				"Details: " + mReportIncidentEntity.getDisastersDetails() + "\n " +

				"District: " + mReportIncidentEntity.getDistrict() + "\n " + "Landmark: "
				+ mReportIncidentEntity.getLandmarks() + "\n " + "Locality: " + mReportIncidentEntity.getLocality()
				+ "\n " + "Name: " + mReportIncidentEntity.getUsername() + "\n " + "Phone: "
				+ mReportIncidentEntity.getPhone() + "\n " + "Zonal Officer Contact: "
				+ mReportIncidentEntity.getOfficerContact() + "\n " + "Zonal Officer Name: "
				+ mReportIncidentEntity.getOfficerName() + "\n ";

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

	// GET THE ZONAL OFFICER
	public int searchZonalOfficer(String locality) {

		int zone = 0;
		if (locality.equals("Chaltlang") || locality.equals("null") || locality.equals("Durtlang")
				|| locality.equals("Durtlang Leitan") || locality.equals("Durtlang North") || locality.equals("Selesih")
				|| locality.equals("Chanmari West") || locality.equals("Edenthar")
				|| locality.equals("Chaltlang Lily Veng")) {
			// zone 1
			zone = 1;
		} else if (locality.equals("Bawngkawn") || locality.equals("Thuampui") || locality.equals("Zuangtui")
				|| locality.equals("Muanna Veng") || locality.equals("Bawngkawn South") || locality.equals("Zemabawk")
				|| locality.equals("Zemabawk North") || locality.equals("Falkland")) {
			// zone 2
			zone = 2;
		} else if (locality.equals("Electric Veng") || locality.equals("Ramhlun")
				|| locality.equals("Ramhlun Sport Complex") || locality.equals("Ramhlun South")
				|| locality.equals("Ramhlun North") || locality.equals("Ramhlun Vengthar")
				|| locality.equals("Ramhlun Venglai") || locality.equals("Ramthar")) {
			// zone 3
			zone = 3;
		} else if (locality.equals("Tuithiang") || locality.equals("Chhinga Veng") || locality.equals("Zarkawt")
				|| locality.equals("Dawrpui") || locality.equals("Chanmari West") || locality.equals("Saron Veng")
				|| locality.equals("Armed Veng") || locality.equals("Armed Veng South") || locality.equals("Chite")) {
			// zone 4
			zone = 4;
		} else if (locality.equals("Tuikual South") || locality.equals("Tuikual North") || locality.equals("Dinthar")
				|| locality.equals("Dawrpui Vengthar") || locality.equals("Chawnpui") || locality.equals("Kanan")
				|| locality.equals("Zotlang") || locality.equals("Hunthar") || locality.equals("Vaivakawn")) {
			// zone 5
			zone = 5;
		} else if (locality.equals("Rangvamual") || locality.equals("Phunchawng") || locality.equals("Tuivamit")
				|| locality.equals("Sakawrtuichhun") || locality.equals("Chawlhhmun") || locality.equals("Luangmual")
				|| locality.equals("Tanhril") || locality.equals("Govt Complex") || locality.equals("Zonuam")) {
			// zone 6
			zone = 6;
		} else if (locality.equals("Upper Republic") || locality.equals("Republic")
				|| locality.equals("Republic Vengthlang") || locality.equals("Venghlui")
				|| locality.equals("College Veng") || locality.equals("Bethlehem")
				|| locality.equals("Bethlehem Vengthlang") || locality.equals("ITI Veng")) {
			// zone 7
			zone = 7;
		} else if (locality.equals("Bungkawn Vengthar") || locality.equals("Maubawk") || locality.equals("Bungkawn")
				|| locality.equals("Khatla") || locality.equals("Khatla South") || locality.equals("Khatla East")
				|| locality.equals("Nursery Veng") || locality.equals("Lawipu")
				|| locality.equals("New Secretariat Complex")) {
			// zone 8
			zone = 8;
		}
		return zone;

	}
	
	//SMS FOR REQUEST RELIEF
	public void smsRelief() {
	    final String uri = "https://instantalerts.co/api/web/send?apikey=6n7h4wv5yte7t87qxp4vmrfh96tu0el7&sender=SEDEMO&to=7810911046&message=Hi%2C+this+is+a+reliefRequest";

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);

	    System.out.println(result);
	}

	//SMS FOR REQUEST RELIEF
	public void smsIncident() {
	    final String uri = "https://instantalerts.co/api/web/send?apikey=6n7h4wv5yte7t87qxp4vmrfh96tu0el7&sender=SEDEMO&to=7810911046&message=Hi%2C+this+is+a+IncidentReport";

	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);

	    System.out.println(result);
	}
}


//
//2019-05-27 09:22:34.330 ERROR 1781 --- [nio-8080-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.mail.MailSendException: Mail server connection failed; nested exception is com.sun.mail.util.MailConnectException: Couldn't connect to host, port: smtp.gmail.com, 587; timeout 5000;
//  nested exception is:
//	java.net.ConnectException: No route to host (connect failed). Failed messages: com.sun.mail.util.MailConnectException: Couldn't connect to host, port: smtp.gmail.com, 587; timeout 5000;
//  nested exception is:
//	java.net.ConnectException: No route to host (connect failed); message exceptions (1) are:
//Failed message 1: com.sun.mail.util.MailConnectException: Couldn't connect to host, port: smtp.gmail.com, 587; timeout 5000;
//  nested exception is:
//	java.net.ConnectException: No route to host (connect failed)] with root cause

