package com.lalthanpuia.addma1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lalthanpuia.addma1.dao.UserRepository;
import com.lalthanpuia.addma1.entity.Incident;
import com.lalthanpuia.addma1.entity.Officer;
import com.lalthanpuia.addma1.entity.User;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	private UserRepository userRepository;

	@Autowired
	public UserEntityServiceImpl (UserRepository theUserRepository) {
		userRepository = theUserRepository;
	}
	
	//@Override
	public List<User> findAll() {
			return userRepository.findAll();
	}

	//@Override
	public void save(User theUserEntity) {
		userRepository.save(theUserEntity);
		
	}

	//@Override
	public User findByUsername(String theUsername) {
		
//		Optional<User> result = userRepository.findByUsername(theUsername);
//		//List<User> result = userRepository.findByUsername(theUsername);
//	
//		
//		User theUserEntity= null;
//		System.out.println("Username in the entity service: "+ theUserEntity.getUsername());
//		
//		if(result.isPresent())
//			theUserEntity=result.get();
//		else
//			throw new RuntimeException("Did not find employee id - "+ theUsername);
//		
//		return theUserEntity;
		
		List<User> result = null;
		try{
			result = userRepository.findByUsername(theUsername);	
		}catch(Exception e) {
			
			
		}
		
		//List<User> result = userRepository.findByUsername(theUsername);
	
		
//		User theUserEntity= null;
//		System.out.println("Username in the entity service: "+ theUserEntity.getUsername());
//		
//		if(result.isPresent())
//			theUserEntity=result.get();
//		else
//			throw new RuntimeException("Did not find employee id - "+ theUsername);
		
		return result.get(0);
	}
	
//	@Override
	public List<User> findByPhoneNo(String thePhoneNo) {

		List<User> result = null;
		try{
			result = userRepository.findByPhoneNo(thePhoneNo);	
		}catch(Exception e) {	}
	
		return result;
	}
	
	/*
	 * @Override public User findByPhoneNo(String thePhoneNo) {
	 * 
	 * Optional<User> result = userRepository.findByPhoneNo(thePhoneNo);
	 * 
	 * User theUserEntity= null;
	 * 
	 * if(result.isPresent()) theUserEntity=result.get(); else throw new
	 * RuntimeException("Did not find employee id - "+ thePhoneNo);
	 * 
	 * return theUserEntity;
	 * 
	 * }
	 */
	
	
}













/*
 //INCIDENT REPORT API
	@PostMapping("/post/incident")
	public String incidentReportInsert(@RequestBody Incident newIncident) {

		System.out.println(newIncident.getUsername());
		System.out.println(newIncident.getLocality());

		
		
		Officer currentZonalOfficer = zonalOfficerService.findByOfficerLocality(newIncident.getLocality());

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


 */


















