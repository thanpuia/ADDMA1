package com.lalthanpuia.addma1.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lalthanpuia.addma1.entity.Incident;
import com.lalthanpuia.addma1.entity.Note;
import com.lalthanpuia.addma1.entity.Officer;
import com.lalthanpuia.addma1.entity.Relief;
import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.notification.AndroidPushNotificationsService;
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
	
	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;
	
	public String TOPIC = "";//this should be unique for every on. as soon as it get the call, it should fetch dynamicall.
	public String TOPIC2 = "7810911046";

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
	@PostMapping("/signup/newUser")
	public Note UserInsert(@RequestBody User newUser) {

		String username = newUser.getUsername();
		String phone = newUser.getPhoneNo();

		System.out.println("username: " + username);
		System.out.println("phone: " + phone);
		
		Note note = new Note();
		note.setSubject("Registration");

		try {
			// CHECK THE AVAILABILITY OF USERNAME BY CALLING IT
			List<User> mUser1 = userEntityService.findByPhoneNo(phone);

			System.out.println("mUser Phnoe: " + mUser1.get(0).getPhoneNo());

			// CHECK THE AVAILABILTIY OF PHONE NUMBER BY CALLING IT;
			/// User mUser2 = userEntityService.findByPhoneNo(phone);

//				if(mUser1 != null) {
//					System.out.println("User name all ready present"+mUser1);
//					if(mUser2 != null) {	}
//					
//				}else {
//					System.out.println("unique name"+mUser1);
//					userEntityService.save(newUser);
//				}	
			System.out.println("Do nothing , they already present");
			note.setBody1("duplicate");
		} catch (Exception e) {
			System.out.println("error: " + e);
			System.out.println("Insert new User!");
			userEntityService.save(newUser);
			
			note.setBody1("saved");
		}
		return note;
	}
	// AUTHENTICATION LOGIN
		@GetMapping("/test/{phoneNo}/{password}")
		public User login(@PathVariable String phoneNo, @PathVariable String password) {

			System.out.println("Phone Number: " + phoneNo);
			System.out.println("Password: " + password);
			//password = "{noop}" + password;

			List<User> mUserList ;
			try {
				// mUser = userEntityService.findByUsername(phoneNo);
				mUserList =  userEntityService.findByPhoneNo(phoneNo);
				User mUser = mUserList.get(0);
				String mPassword = mUser.getPassword();
				System.out.println("Real userpassowrd: " + mPassword + " " + mUser.getAltContactName());
				if (mPassword.equals(password)) {
				} else {
					mUser = null;
					System.out.println("INside if Else: ");
					return mUser;
				}
			} catch (Exception e) {
				System.out.println("User inside catch:" + mUser.getUsername());
				//mUser = null;
				return mUser;
			}

			System.out.println("User otsi:" + mUser.getUsername());
			return mUser;
		}

	// REQUEST RELIEF API
	@PostMapping("/post/relief")
	public String requestReliefInsert(@RequestBody Relief newRelief) {

		// GET THE ZONAL OFFICER
		String locality = newRelief.getLocality();
		System.out.println("Locality: " + newRelief.getLocality());
		System.out.println("Test: " + newRelief.getLocality()+newRelief.getDetails()+newRelief.getMaterial()+newRelief.getLocation()+newRelief.getLocality()+newRelief.getOfficerName());

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

		//UPLOAD TO RELIEF REPORT TABLE
		requestReliefMaterialService.save(newRelief);

		///GET THE LATEST UPDATE . ONLY THE LATEST ONE. SINGLE OBJECT
		Relief mLatestRelief = (Relief) requestReliefMaterialService.findFirst1ByOrderBySerialNumberDesc();
		//sent sms to zonal officer
		
		smsRelief();
		
		// sent to mail
		reliefSendMail(newRelief);

		//SENT TO CLIENT USER ANDROID NOTIFICATION
		sendUserRelief(newRelief);
		sendZonalOfficerRelief(newRelief);
		
		
		System.out.println("Checking post relief: " + newRelief.getMaterial());

		return "save";
	}

	//INCIDENT REPORT API
/*	@PostMapping("/post/incident")
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
		//newIncident.setZoneId(zoneIdStr);
		newIncident.setOfficerZone(officerZone);

		//sent sms to zonal officer
		//smsIncident();
		
		// sent to mail
		//incidentSendMail(newIncident);
		
		//newIncident serial khi a zero 
		
		// sent to client user android notification
		System.out.println("First function: "+newIncident.getUsername());
		send(newIncident);
		
		//
		
		//GET ALL THE REPROT OF A THIS USER ONLY (INDIVIDUAL POST)
		//List<Incident> theUserIncident = reportIncidentService.findByPhone(newIncident.getPhone());
		//System.out.println("This is life"+theUserIncident.get(0).getPhone());	
	
		// sent to client user android notification
		//System.out.println("First function: "+newIncident.getUsername());
	//	send(theUserIncident);
		
		// UPLOAD TO INCIDENT REPORT TABLE
		reportIncidentService.save(newIncident);

		return "save";
	}
*/
	
	//INCIDENT REPORT API
	@PostMapping("/post/incident")
	public String incidentReportInsert(@RequestBody Incident newIncident) {

		System.out.println(newIncident.getUsername());
		System.out.println(newIncident.getLocality());

		
		
		Officer currentZonalOfficer = zonalOfficerService.findByOfficerLocality(newIncident.getLocation());

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
		//newIncident.setZoneId(zoneIdStr);
		newIncident.setOfficerZone(officerZone);
		
		// UPLOAD TO INCIDENT REPORT TABLE
		reportIncidentService.save(newIncident);
		
		Incident myLatestIncident = (Incident) reportIncidentService.findFirst1ByOrderBySerialNumberDesc() ;
		
		System.out.println(myLatestIncident.getSerialNumber());
		//sent sms to zonal officer
		//smsIncident();
		
		// sent to mail
		//incidentSendMail(newIncident);
		
		
		// sent to client user android notification
		System.out.println("First function: "+newIncident.getUsername());
		sendUser(newIncident);
		sendZonalOfficer(newIncident);

		//
		
		//GET ALL THE REPROT OF A THIS USER ONLY (INDIVIDUAL POST)
		//List<Incident> theUserIncident = reportIncidentService.findByPhone(newIncident.getPhone());
		//System.out.println("This is life"+theUserIncident.get(0).getPhone());	
	
		// sent to client user android notification
		//System.out.println("First function: "+newIncident.getUsername());
	//	send(theUserIncident);
		


		return "save";
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
	
	//SENDING CLIENT NOTIFICATION THROUGH FIREBASE CLOUD MESSAGE (FCM)
//	@RequestMapping(value = "/sendd", method = RequestMethod.GET, produces = "application/json")
//	public ResponseEntity<String> send() throws JSONException {
//		
//		//EXTRACT ALL THE REQUIRED DATA FOR NOTIFICATION
////		String disasterType = mIncident.getDisasterType();
////		String locality = mIncident.getLocality();
////		String landmarks = mIncident.getLandmarks();
////		String details = mIncident.getDetails();
////		String district = mIncident.getDistrict();
////		String inLocation = mIncident.getInLocation();
////		String lat = mIncident.getLat();
////		String lng = mIncident.getLng();
////		String location = mIncident.getLocation();
////		String username = mIncident.getUsername();
////		String phone = mIncident.getPhone();
////		String reportOn =mIncident.getReportOn();
////		String status =mIncident.getStatus();
////		String userId = mIncident.getUserId();
////		String officerContact = mIncident.getOfficerContact();
////		String officerName = mIncident.getOfficerName();
////		String officerZone = mIncident.getOfficerZone();
////		String zoneId = mIncident.getZoneId();
////		String officerId = mIncident.getOfficerId();
//
//		//CHANGE THE TOPIC SO THAT ONLY ONE CLIENT GET THE NOTIFICATION IT DESERVE
//		TOPIC = "7810911046";//phone;	
//		
//		JSONObject body = new JSONObject();
//		body.put("to","/topics"+TOPIC);
//		body.put("priority","high");
//		
//		JSONObject notification = new JSONObject();
//		notification.put("title","JSA Notify");
//		notification.put("body","Happy Friday");
//		
//		
//		//INSERT THE DATA OF THE CLIENT NOTIFICATION
//		JSONObject data = new JSONObject();
//		data.put("key1","DATA1");
//		data.put("key2","DATA2");
//		
////		data.put("disasterType",disasterType);
////		data.put("locality",locality);
////		data.put("landmarks",landmarks);
////		data.put("details",details);
////		data.put("district",district);
////		data.put("inLocation",inLocation);
////		data.put("lat",lat);
////		data.put("lng",lng);
////		data.put("location",location);
////		data.put("username",username);
////		data.put("phone",phone);
////		data.put("reportOn",reportOn);
////		data.put("status",status);
////		data.put("userId",userId);
////		data.put("officerContact",officerContact);
////		data.put("officerName",officerName);
////		data.put("officerZone",officerZone);
////		data.put("zoneId",zoneId);
////		data.put("officerId",officerId);
//
//		body.put("notification", notification);
//		body.put("data", data);
//		
//		HttpEntity<String> request = new HttpEntity<String>(body.toString());
//		
//		CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
//		CompletableFuture.allOf(pushNotification).join();
//		
//		
//	    System.out.println("inside FCM");
//
//		try {
//		    System.out.println("inside FCM try block");
//
//			String firebaseResponse = pushNotification.get();
//			return new ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
//		}catch (InterruptedException e) {
//		    System.out.println("inside FCM catch");
//			e.printStackTrace();
//		}catch (ExecutionException e) {
//		    System.out.println("inside FCM catch");
//			e.printStackTrace();
//		}
//		
//		return new ResponseEntity<String> ("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
//	}
	
	
	
	
	//*****************************
	//FOR INCIDENT
	//*****************************
	 @RequestMapping(value = "/sendUser", method = RequestMethod.GET, produces = "application/json")
	  public ResponseEntity<String> sendUser(Incident mIncident) throws JSONException {
			System.out.println("First function: "+mIncident.getUsername());

		//EXTRACT ALL THE REQUIRED DATA FOR NOTIFICATION
		int serialNumber = mIncident.getSerialNumber();
		String disasterType = mIncident.getDisasterType();
		String locality = mIncident.getLocality();
		String landmarks = mIncident.getLandmarks();
		String details = mIncident.getDetails();
		String district = mIncident.getDistrict();
		String inLocation = mIncident.getInLocation();
		String lat = mIncident.getLat();
		String lng = mIncident.getLng();
		String location = mIncident.getLocation();
		String username = mIncident.getUsername();
		String phone = mIncident.getPhone();
		String reportOn =mIncident.getReportOn();
		String status =mIncident.getStatus();
		String userId = mIncident.getUserId();
		String officerContact = mIncident.getOfficerContact();
		String officerName = mIncident.getOfficerName();
		String officerZone = mIncident.getOfficerZone();
		String zoneId = mIncident.getZoneId();
		String officerId = mIncident.getOfficerId();
		
		TOPIC = phone;
		
	    JSONObject body = new JSONObject();
	    body.put("to", "/topics/" + TOPIC);
	    body.put("priority", "high");
	 
	    JSONObject notification = new JSONObject();
	    notification.put("title", disasterType);
	    notification.put("body", username);
	    
	    JSONObject data = new JSONObject();
//	    data.put("Key-1", "JSA Data 1");
//	    data.put("Key-2", "JSA Data 2");
	    
	    data.put("serialNumber",serialNumber);
	    data.put("disasterType",disasterType);
	    data.put("locality",locality);
	    data.put("landmarks",landmarks);
	    data.put("details",details);
	    data.put("district",district);
		data.put("inLocation",inLocation);
		data.put("lat",lat);
		data.put("lng",lng);
		data.put("location",location);
		data.put("username",username);
		data.put("phone",phone);
		data.put("reportOn",reportOn);
		data.put("status",status);
		data.put("userId",userId);
		data.put("officerContact",officerContact);
		data.put("officerName",officerName);
		data.put("officerZone",officerZone);
		data.put("zoneId",zoneId);
		data.put("officerId",officerId);
		System.out.println(phone);
		
	    body.put("notification", notification);
	    body.put("data", data);
	    
	    
	    
	/**
	    {
	       "notification": {
	          "title": "JSA Notification",
	          "body": "Happy Message!"
	       },
	       "data": {
	          "Key-1": "JSA Data 1",
	          "Key-2": "JSA Data 2"
	       },
	       "to": "/topics/JavaSampleApproach",
	       "priority": "high"
	    }
	*/
	 
	    HttpEntity<String> request = new HttpEntity<String>(body.toString());
	 
	    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
	    CompletableFuture.allOf(pushNotification).join();
	 
	    System.out.println("inside FCM");
	    try {
	      String firebaseResponse = pushNotification.get();
		    System.out.println("inside FCM try block");

	      return new ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
	    } catch (InterruptedException e) {
		    System.out.println("inside FCM catch");

	      e.printStackTrace();
	    } catch (ExecutionException e) {
		    System.out.println("inside FCM catch");

	      e.printStackTrace();
	    }
	 
	    return new ResponseEntity<String>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	  }
	 
	 @RequestMapping(value = "/sendZonalOfficer", method = RequestMethod.GET, produces = "application/json")
	  public ResponseEntity<String> sendZonalOfficer(Incident mIncident) throws JSONException {
			System.out.println("First function: "+mIncident.getUsername());

		//EXTRACT ALL THE REQUIRED DATA FOR NOTIFICATION
		int serialNumber = mIncident.getSerialNumber();
		String disasterType = mIncident.getDisasterType();
		String locality = mIncident.getLocality();
		String landmarks = mIncident.getLandmarks();
		String details = mIncident.getDetails();
		String district = mIncident.getDistrict();
		String inLocation = mIncident.getInLocation();
		String lat = mIncident.getLat();
		String lng = mIncident.getLng();
		String location = mIncident.getLocation();
		String username = mIncident.getUsername();
		String phone = mIncident.getPhone();
		String reportOn =mIncident.getReportOn();
		String status =mIncident.getStatus();
		String userId = mIncident.getUserId();
		String officerContact = mIncident.getOfficerContact();
		String officerName = mIncident.getOfficerName();
		String officerZone = mIncident.getOfficerZone();
		String zoneId = mIncident.getZoneId();
		String officerId = mIncident.getOfficerId();
		
		
	    JSONObject body = new JSONObject();
	    body.put("to", "/topics/" + officerContact);
	    body.put("priority", "high");
	 
	    JSONObject notification = new JSONObject();
	    notification.put("title", disasterType);
	    notification.put("body", username);
	    
	    JSONObject data = new JSONObject();
//	    data.put("Key-1", "JSA Data 1");
//	    data.put("Key-2", "JSA Data 2");
	    
	    data.put("serialNumber",serialNumber);
	    data.put("disasterType",disasterType);
	    data.put("locality",locality);
	    data.put("landmarks",landmarks);
	    data.put("details",details);
	    data.put("district",district);
		data.put("inLocation",inLocation);
		data.put("lat",lat);
		data.put("lng",lng);
		data.put("location",location);
		data.put("username",username);
		data.put("phone",phone);
		data.put("reportOn",reportOn);
		data.put("status",status);
		data.put("userId",userId);
		data.put("officerContact",officerContact);
		data.put("officerName",officerName);
		data.put("officerZone",officerZone);
		data.put("zoneId",zoneId);
		data.put("officerId",officerId);
		System.out.println(phone);
		
	    body.put("notification", notification);
	    body.put("data", data);
	    
	    
	    
	/**
	    {
	       "notification": {
	          "title": "JSA Notification",
	          "body": "Happy Message!"
	       },
	       "data": {
	          "Key-1": "JSA Data 1",
	          "Key-2": "JSA Data 2"
	       },
	       "to": "/topics/JavaSampleApproach",
	       "priority": "high"
	    }
	*/
	 
	    HttpEntity<String> request = new HttpEntity<String>(body.toString());
	 
	    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
	    CompletableFuture.allOf(pushNotification).join();
	 
	    System.out.println("inside FCM");
	    try {
	      String firebaseResponse = pushNotification.get();
		    System.out.println("inside FCM try block");

	      return new ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
	    } catch (InterruptedException e) {
		    System.out.println("inside FCM catch");

	      e.printStackTrace();
	    } catch (ExecutionException e) {
		    System.out.println("inside FCM catch");

	      e.printStackTrace();
	    }
	 
	    return new ResponseEntity<String>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	  }
	 
	 

		//*****************************
		//FOR RELIEF
		//*****************************
		 @RequestMapping(value = "/sendUserRelief", method = RequestMethod.GET, produces = "application/json")
		  public ResponseEntity<String> sendUserRelief(Relief mRelief) throws JSONException {
				System.out.println("First function: "+mRelief.getUsername());

			//EXTRACT ALL THE REQUIRED DATA FOR NOTIFICATION
			int serialNumber = mRelief.getSerialNumber();
			String details = mRelief.getDetails();
			String district = mRelief.getDistrict();
			String landmarks = mRelief.getLandmarks();
			String lat =mRelief.getLat();
			String lng = mRelief.getLng();
			String locality = mRelief.getLocality();
			String material = mRelief.getMaterial();
			String materialId = mRelief.getMaterialId();
			String username = mRelief.getUsername();
			String phone = mRelief.getPhone();
			String quantity  = mRelief.getQuantity();
			String requestOn = mRelief.getRequestOn();
			String location = mRelief.getLocation();
			String status = mRelief.getStatus();
			String userId = mRelief.getUserId();
			String officerContact = mRelief.getOfficerContact();
			String officerId = mRelief.getOfficerId();
			String officerName = mRelief.getOfficerName();
			String zoneId = mRelief.getZoneId();
			String officerZone  = mRelief.getOfficerZone();
			
			TOPIC = phone;
			
		    JSONObject body = new JSONObject();
		    body.put("to", "/topics/" + TOPIC);
		    body.put("priority", "high");
		 
		    JSONObject notification = new JSONObject();
		    notification.put("title", material);
		    notification.put("body", username);
		    
		    JSONObject data = new JSONObject();
//		    data.put("Key-1", "JSA Data 1");
//		    data.put("Key-2", "JSA Data 2");
		    
		    data.put("serialNumber",serialNumber);
		    data.put("details",details);
		    data.put("district",district);
		    data.put("landmarks",landmarks);
		    data.put("lat",lat);
			data.put("lng",lng);
			data.put("locality",locality);
			data.put("material",material);
			data.put("materialId",materialId);
			data.put("username",username);
			data.put("phone",phone);
			data.put("quantity",quantity);
			data.put("requestOn", requestOn);
			data.put("quantity",quantity);
		    data.put("requestOn", requestOn);
		    data.put("location",location);
		    data.put("status",status);
		    data.put("userId", userId);
			data.put("officerContact",officerContact);
			data.put("officerName",officerName);
			data.put("officerZone",officerZone);
			data.put("zoneId",zoneId);
			data.put("officerId",officerId);
			
			System.out.println(phone);
			
		    body.put("notification", notification);
		    body.put("data", data);
		 
		    HttpEntity<String> request = new HttpEntity<String>(body.toString());
		 
		    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		    CompletableFuture.allOf(pushNotification).join();
		 
		    System.out.println("inside FCM");
		    try {
		      String firebaseResponse = pushNotification.get();
			  System.out.println("inside FCM try block");

		      return new ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
		    } catch (InterruptedException e) {
			    System.out.println("inside FCM catch");

		      e.printStackTrace();
		    } catch (ExecutionException e) {
			    System.out.println("inside FCM catch");

		      e.printStackTrace();
		    }
		 
		    return new ResponseEntity<String>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
		  }
		 
 //*****************************************************************
		 
		 @RequestMapping(value = "/sendZonalOfficerRelief", method = RequestMethod.GET, produces = "application/json")
		  public ResponseEntity<String> sendZonalOfficerRelief(Relief mRelief) throws JSONException {
				
			System.out.println("First function: "+mRelief.getUsername());

			//EXTRACT ALL THE REQUIRED DATA FOR NOTIFICATION
			int serialNumber = mRelief.getSerialNumber();
			String details = mRelief.getDetails();
			String district = mRelief.getDistrict();
			String landmarks = mRelief.getLandmarks();
			String lat =mRelief.getLat();
			String lng = mRelief.getLng();
			String locality = mRelief.getLocality();
			String material = mRelief.getMaterial();
			String materialId = mRelief.getMaterialId();
			String username = mRelief.getUsername();
			String phone = mRelief.getPhone();
			String quantity  = mRelief.getQuantity();
			String requestOn = mRelief.getRequestOn();
			String location = mRelief.getLocation();
			String status = mRelief.getStatus();
			String userId = mRelief.getUserId();
			String officerContact = mRelief.getOfficerContact();
			String officerId = mRelief.getOfficerId();
			String officerName = mRelief.getOfficerName();
			String zoneId = mRelief.getZoneId();
			String officerZone  = mRelief.getOfficerZone();
			
		    JSONObject body = new JSONObject();
		    body.put("to", "/topics/" + officerContact);
		    body.put("priority", "high");
		 
		    JSONObject notification = new JSONObject();
		    notification.put("title", material);
		    notification.put("body", username);
		    
		    JSONObject data = new JSONObject();
//		    data.put("Key-1", "JSA Data 1");
//		    data.put("Key-2", "JSA Data 2");
		    
		    data.put("serialNumber",serialNumber);
		    data.put("details",details);
		    data.put("district",district);
		    data.put("landmarks",landmarks);
		    data.put("lat",lat);
			data.put("lng",lng);
			data.put("locality",locality);
			data.put("material",material);
			data.put("materialId",materialId);
			data.put("username",username);
			data.put("phone",phone);
			data.put("quantity",quantity);
			data.put("requestOn", requestOn);
			data.put("quantity",quantity);
		    data.put("requestOn", requestOn);
		    data.put("location",location);
		    data.put("status",status);
		    data.put("userId", userId);
			data.put("officerContact",officerContact);
			data.put("officerName",officerName);
			data.put("officerZone",officerZone);
			data.put("zoneId",zoneId);
			data.put("officerId",officerId);
			
		    body.put("notification", notification);
		    body.put("data", data);

		    HttpEntity<String> request = new HttpEntity<String>(body.toString());
		 
		    CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
		    CompletableFuture.allOf(pushNotification).join();
		 
		    System.out.println("inside FCM");
		    try {
		      String firebaseResponse = pushNotification.get();
			    System.out.println("inside FCM try block");

		      return new ResponseEntity<String>(firebaseResponse, HttpStatus.OK);
		    } catch (InterruptedException e) {
			    System.out.println("inside FCM catch");

		      e.printStackTrace();
		    } catch (ExecutionException e) {
			    System.out.println("inside FCM catch");

		      e.printStackTrace();
		    }
		 
		    return new ResponseEntity<String>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
		  }
		 
	 	// notification
		@GetMapping("/notifyOfficer/{officerContact}")
		public List<Incident> getNotificationOfficer(@PathVariable String officerContact) {
		
			System.out.println("OfficerContact: " +officerContact);
			//only for officer: 
			//List<Incident> notify= reportIncidentService.findByOfficerId(officerId);
			List<Incident> notify= reportIncidentService.findByOfficerContact(officerContact);

			return notify;
			}
		
		
		//find all the notification for a single user. using its phone number
	 	// notification
		@GetMapping("/notifyCitizen/{phone}")
		public List<Incident> getNotificationCitizen(@PathVariable String phone) {
		
			//System.out.println("OfficerContact: " +officerContact);
			//only for officer: 
			//List<Incident> notify= reportIncidentService.findByOfficerId(officerId);
			List<Incident> notify= reportIncidentService.findByPhone(phone);

			return notify;	
		}
		
		
//***********************************************************************

	//	NOTIfication kha a buaithlak dawn ta
		
		
		
		//EDIT THE INCIDENT STATUS
		@GetMapping("statusChange/{incidentId}/{status}")
		public String changeStatus(@PathVariable int incidentId, @PathVariable String status) {
			
			//Incident inc = reportIncidentService.findBySerialNumber(incidentId, status);
			Incident inc = reportIncidentService.findBySerialNumber(incidentId);
			
			if(status.equals("notSeen")) {
				status ="Not Seen";
			}
			inc.setStatus(status);
			
			reportIncidentService.save(inc);
			System.out.println("Status: "+inc.getStatus());
			return "change";
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


//GET THE ZONAL OFFICER
/*
 * public int searchZonalOfficer(String locality) {
 * 
 * int zone = 0; if (locality.equals("Chaltlang") || locality.equals("null") ||
 * locality.equals("Durtlang") || locality.equals("Durtlang Leitan") ||
 * locality.equals("Durtlang North") || locality.equals("Selesih") ||
 * locality.equals("Chanmari West") || locality.equals("Edenthar") ||
 * locality.equals("Chaltlang Lily Veng")) { // zone 1 zone = 1; } else if
 * (locality.equals("Bawngkawn") || locality.equals("Thuampui") ||
 * locality.equals("Zuangtui") || locality.equals("Muanna Veng") ||
 * locality.equals("Bawngkawn South") || locality.equals("Zemabawk") ||
 * locality.equals("Zemabawk North") || locality.equals("Falkland")) { // zone 2
 * zone = 2; } else if (locality.equals("Electric Veng") ||
 * locality.equals("Ramhlun") || locality.equals("Ramhlun Sport Complex") ||
 * locality.equals("Ramhlun South") || locality.equals("Ramhlun North") ||
 * locality.equals("Ramhlun Vengthar") || locality.equals("Ramhlun Venglai") ||
 * locality.equals("Ramthar")) { // zone 3 zone = 3; } else if
 * (locality.equals("Tuithiang") || locality.equals("Chhinga Veng") ||
 * locality.equals("Zarkawt") || locality.equals("Dawrpui") ||
 * locality.equals("Chanmari West") || locality.equals("Saron Veng") ||
 * locality.equals("Armed Veng") || locality.equals("Armed Veng South") ||
 * locality.equals("Chite")) { // zone 4 zone = 4; } else if
 * (locality.equals("Tuikual South") || locality.equals("Tuikual North") ||
 * locality.equals("Dinthar") || locality.equals("Dawrpui Vengthar") ||
 * locality.equals("Chawnpui") || locality.equals("Kanan") ||
 * locality.equals("Zotlang") || locality.equals("Hunthar") ||
 * locality.equals("Vaivakawn")) { // zone 5 zone = 5; } else if
 * (locality.equals("Rangvamual") || locality.equals("Phunchawng") ||
 * locality.equals("Tuivamit") || locality.equals("Sakawrtuichhun") ||
 * locality.equals("Chawlhhmun") || locality.equals("Luangmual") ||
 * locality.equals("Tanhril") || locality.equals("Govt Complex") ||
 * locality.equals("Zonuam")) { // zone 6 zone = 6; } else if
 * (locality.equals("Upper Republic") || locality.equals("Republic") ||
 * locality.equals("Republic Vengthlang") || locality.equals("Venghlui") ||
 * locality.equals("College Veng") || locality.equals("Bethlehem") ||
 * locality.equals("Bethlehem Vengthlang") || locality.equals("ITI Veng")) { //
 * zone 7 zone = 7; } else if (locality.equals("Bungkawn Vengthar") ||
 * locality.equals("Maubawk") || locality.equals("Bungkawn") ||
 * locality.equals("Khatla") || locality.equals("Khatla South") ||
 * locality.equals("Khatla East") || locality.equals("Nursery Veng") ||
 * locality.equals("Lawipu") || locality.equals("New Secretariat Complex")) { //
 * zone 8 zone = 8; } return zone;
 * 
 * }
 */

