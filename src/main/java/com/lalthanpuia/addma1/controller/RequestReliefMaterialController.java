package com.lalthanpuia.addma1.controller;

import java.util.Optional;

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

import com.lalthanpuia.addma1.entity.RequestReliefMaterialEntity;
import com.lalthanpuia.addma1.entity.UserEntity;
import com.lalthanpuia.addma1.service.RequestReliefMaterialService;
import com.lalthanpuia.addma1.service.UserEntityService;

@Controller
@RequestMapping("/request")
public class RequestReliefMaterialController {

	private RequestReliefMaterialService requestReliefMaterialService;
	private UserEntityService userEntityService;
	
	@Autowired
	private JavaMailSender sender;
	
	public RequestReliefMaterialController (RequestReliefMaterialService theRequestReliefMaterialService, UserEntityService theUserEntityService) {
		
		this.requestReliefMaterialService = theRequestReliefMaterialService;
		this.userEntityService = theUserEntityService;
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
	public String saveRequestRelief (@ModelAttribute("requestReliefMaterial") RequestReliefMaterialEntity theRequestReliefMaterialEntity) {
		
		
		//ADD THE USER KNOWN USER DETAILS
		//UserEntity myUserEntity = findById(0);
				
		theRequestReliefMaterialEntity.setDistrict ("districtTesting");
		//theRequestReliefMaterialEntity.setLocality(myUserEntity.getLocality());
		
		
		requestReliefMaterialService.save(theRequestReliefMaterialEntity);
		
		//SEND THE REQUEST DETAILS TO THE MAIL
		sendMail(theRequestReliefMaterialEntity);
		return "home";
		
	}

	@GetMapping("/prepopulater")
	public String prepopu() {
		
//		RequestReliefMaterialEntity theRequestReliefMaterialEntity = new RequestReliefMaterialEntity();
		
//		theModel.addAttribute("requestReliefMaterial", theRequestReliefMaterialEntity);
		
		//TRYING TO GET THE USER NAME
//		String username  ="";
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if(principal instanceof UserDetails) 
//			 username = ((UserDetails)principal).getUsername();
//		else username = principal.toString();
//		
//		theModel.addAttribute("username", username);
//		System.out.println(""+username);
		
		UserEntity myUserEntity = getUserDetails(1);
		System.out.println(myUserEntity.getEmail());
		
		return "/";
	}
	
	//GET USER DETAILS
	public UserEntity getUserDetails(int theId) {
		
		return 	userEntityService.findById(theId);
	}
	
	
	
	//FOR SENDING MAIL
	@RequestMapping ("/sendMail")
	public String sendMail(RequestReliefMaterialEntity mRequestReliefMaterialEntity) {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		
		String subject = 
				"Request Relief for "+ mRequestReliefMaterialEntity.getDetails()+
				" from " + mRequestReliefMaterialEntity.getUsername();

		String messageBody = 
				"District: " + mRequestReliefMaterialEntity.getDistrict() +"\n "+
				"Landmark: " + mRequestReliefMaterialEntity.getLandmarks()+"\n "+
				"Locality: " + mRequestReliefMaterialEntity.getLocality()+"\n "+
				"Material: " + mRequestReliefMaterialEntity.getMaterial()+"\n "+
				"Quantity: " + mRequestReliefMaterialEntity.getQuantity()+"\n "+
				"Name: " + mRequestReliefMaterialEntity.getUsername()+"\n "+
				"Phone: " + mRequestReliefMaterialEntity.getPhone()+"\n "+
				"Zonal Officer Name: " + mRequestReliefMaterialEntity.getZonalOfficerName()+"\n ";
		
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
