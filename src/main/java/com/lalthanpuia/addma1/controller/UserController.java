package com.lalthanpuia.addma1.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.lalthanpuia.addma1.entity.UserEntity;
import com.lalthanpuia.addma1.service.UserEntityService;

@Controller
public class UserController {

	private UserEntityService userEntityService;

	@Autowired
	private JavaMailSender sender;
	
	public  UserController (UserEntityService theUserEntityService) {
		
		this.userEntityService = theUserEntityService;
	}
	
	@GetMapping("/userDetails")
	public String listUserDetails(Model theModel) {
		
		List<UserEntity> theUserEntity = userEntityService.findAll();
		
		theModel.addAttribute("users", theUserEntity);
		
		return "showUserDetails";
	}

	//SIGNUP HANDLER 1
	@GetMapping("/signUp")
	public String userSignUpForm (Model theModel) {
		
		UserEntity theUserEntity = new UserEntity();
		
		theModel.addAttribute("userEntity", theUserEntity);
	
		return "/signUp";
	}

	//SIGNUP HANDLER 2
	@PostMapping("/showFormForAdd")
	public String saveData(@ModelAttribute("userEntity") UserEntity theUserEntity) {
		
		userEntityService.save(theUserEntity);
		
		return "redirect:/userDetails";
	}
	
	@GetMapping("/index")
	public String home() {
	
		return "/home";
	}
//	ORIGINAL
//	@GetMapping("/showAll")
//	public String showall(Model theModel) {
//		String username  ="";
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if(principal instanceof UserDetails) 
//			 username = ((UserDetails)principal).getUsername();
//		else username=principal.toString();
//		
//		theModel.addAttribute("username", username);
//		System.out.println(""+username);
//		
//		//TRYING TO GET THE USER DETAILS HERE
//		
//		
//		return "/main-page";
//	}
	
	
	
//	DUPLICATE
	@GetMapping("/showAll")
	public String showall(Model theModel) {
		String username  ="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) 
			 username = ((UserDetails)principal).getUsername();
		else username=principal.toString();
		
		theModel.addAttribute("username", username);
		
		theModel = theModel.addAttribute("username", username);
		System.out.println(""+username);
		
		//TRYING TO GET THE USER DETAILS HERE
		
	//	sendMail(username);
		return "/main-page";
	}
	
	@GetMapping("/login")
	public String signIn(Model theModel) {
		String username  ="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) 
			 username = ((UserDetails)principal).getUsername();
		else username=principal.toString();
		
		theModel.addAttribute("username", username);
		System.out.println(""+username);
		
		return "/login";
	}
	
	@RequestMapping ("/sendMail")
	public String sendMail(String username) {
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		

        try {
            helper.setTo("thanpuia46@gmail.com");
            helper.setText(username);
            helper.setSubject("Mail From Spring Boot");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
	}
	
	
//	
//	@GetMapping("/phoneAuth")
//	public String showIndex() {
//		return "index";
//	}
//	
//	@GetMapping("/userDetails")
//	public String listAll (Model theModel) {
//	
//		List<UserEntity> theUserEntity = theUserEntityService.findAll();
//		theModel.addAttribute ("users", theUserEntity);
//								
//		return "showUserDetails";
//	}
//						
//	//SIGNUP HANDLER 1
//	@GetMapping("/signUp")
//	public String signup (Model theModel) {
//		
//		UserEntity theUserEntity = new UserEntity();
//		theModel.addAttribute("userEntity", theUserEntity);
//		
//		return "/signUp";
//	}
//	//SIGNUP HANDLER 2
//	@RequestMapping(value="/showFormForAdd",method = RequestMethod.POST)
//	public String showFormForgAdd (@Valid UserEntity theUserEntity, BindingResult bindingResult, Model theModel) {
//		
//		if(bindingResult.hasErrors()) {
//			return "signUp";
//		}else {
//			theUserEntityService.save(theUserEntity);
//			return "redirect:/userDetails";
//		}
//		       
//	}	
//	
//
//
//	
//	
//
//	@GetMapping("testing")
//	public String testing() {
//		String username="";
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if(principal instanceof UserDetails) {
//			username = ((UserDetails)principal).getUsername();
//		}else {
//			username=principal.toString();
//		}
//		
//		System.out.println(""+username);
//		return "/home";
//	}
//	@GetMapping("/reportIncident")
//	public String reportincidented() {
//		
//		
//		return "/reportIncident";
//		//return "/signUp";
//	}
//	
//	@RequestMapping(value="/logout", method = RequestMethod.GET)
//	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    if (auth != null){    
//	        new SecurityContextLogoutHandler().logout(request, response, auth);
//	    }
//	    return "/home";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
//	}
	//Ahm
//	//CHECKING USER
//	 @PreAuthorize("isAuthenticated()")
//	 @RequestMapping(params = "onlyForAuthenticated")
//	 public ModelAndView onlyForAuthenticatedUsers() {
//		 System.out.println ("THis guy is authenticated!!!!!!!!");
//		return null;
//	     
//	 }
}