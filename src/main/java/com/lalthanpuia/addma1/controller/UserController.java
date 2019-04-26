package com.lalthanpuia.addma1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lalthanpuia.addma1.dao.UserRepository;
import com.lalthanpuia.addma1.entity.UserEntity;

@Controller
public class UserController {

	private UserRepository userRepository;

	public  UserController (UserRepository theUserRepository) {
		
		userRepository = theUserRepository;
	}
	
	@GetMapping("/phoneAuth")
	public String showIndex() {
		return "index";
	}
	@GetMapping("/userDetails")
	public String listAll (Model theModel) {
	
		List<UserEntity> theUserEntity = userRepository.findAll();
		theModel.addAttribute ("users", theUserEntity);
								
		return "showUserDetails";
	}
	
	//SIGNUP HANDLER 1
	@GetMapping("/signUp")
	public String signup (Model theModel) {
		
		UserEntity theUserEntity = new UserEntity();
		theModel.addAttribute("userEntity", theUserEntity);
		
		return "/signUp";
	}
	//SIGNUP HANDLER 2
	@RequestMapping(value="/showFormForAdd",method = RequestMethod.POST)
	public String showFormForgAdd (@Valid UserEntity theUserEntity, BindingResult bindingResult, Model theModel) {
		
		if(bindingResult.hasErrors()) {
			return "signUp";
		}else {
			userRepository.save(theUserEntity);
			return "redirect:/userDetails";
		}
		
	}	
	

	
	@GetMapping("/login")
	public String signIn() {
		
		return "/login";
	}
	
	@GetMapping("/showAdminAll")
	public String home() {
		
		return "/home";
	}
	
	@GetMapping("testing")
	public String testing() {
		String username="";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		}else {
			username=principal.toString();
		}
		
		System.out.println(""+username);
		return "/home";
	}
	@GetMapping("/reportIncident")
	public String reportincidented() {
		
		
		return "/reportIncident";
		//return "/signUp";
	}
	
//	//CHECKING USER
//	 @PreAuthorize("isAuthenticated()")
//	 @RequestMapping(params = "onlyForAuthenticated")
//	 public ModelAndView onlyForAuthenticatedUsers() {
//		 System.out.println ("THis guy is authenticated!!!!!!!!");
//		return null;
//	     
//	 }
}