package com.lalthanpuia.addma1.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lalthanpuia.addma1.entity.User;
import com.lalthanpuia.addma1.notification.AndroidPushNotificationsService;
import com.lalthanpuia.addma1.service.UserEntityService;


@Controller
public class UserController {

	private UserEntityService userEntityService;
	private final String TOPIC = "7810911046";//this should be unique for every on. as soon as it get the call, it should fetch dynamicall.
	  
	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;
	
	@Autowired
	private JavaMailSender sender;
	
	public  UserController (UserEntityService theUserEntityService) {
		
		this.userEntityService = theUserEntityService;
	}
	
	@GetMapping("/sms")
	public String sendSMS() {
		
		return "SMS";
	}
	@GetMapping("/userDetails")
	public String listUserDetails(Model theModel) {
		
		List<User> theUserEntity = userEntityService.findAll();
		
		theModel.addAttribute("users", theUserEntity);
		
		return "showUserDetails";
	}

	//SIGNUP HANDLER 1
	@GetMapping("/signUp")
	public String userSignUpForm (Model theModel) {
		
		User theUserEntity = new User();
		
		theModel.addAttribute("userEntity", theUserEntity);
	
		return "/signUp";
	}

	//SIGNUP HANDLER 2
	@PostMapping("/showFormForAdd")
	public String saveData(@ModelAttribute("userEntity") User theUserEntity) {
		
		//puttiing ({noop} in front of the password so the the database can do someithing , i dont know exactly why we add this, but without this it do not work
		theUserEntity.setPassword("{noop}"+theUserEntity.getPassword());
		
		userEntityService.save(theUserEntity);
		
		//return "redirect:/userDetails";
		return "redirect:/showAll";

	}
	
	@GetMapping("/index")
	public String home() {
	
		return "redirect:/showAll";
		//return "/home";
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
		
		if(username.equals("anonymousUser")) {
			username = "Guest";
			System.out.println(""+username);
		}
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
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	
	 @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
	  public ResponseEntity<String> send() throws JSONException {
	 
	    JSONObject body = new JSONObject();
	    body.put("to", "/topics/" + TOPIC);
	    body.put("priority", "high");
	 
	    JSONObject notification = new JSONObject();
	    notification.put("title", "JSA Notification");
	    notification.put("body", "Happy Message!");
	    
	    JSONObject data = new JSONObject();
	    data.put("Key-1", "JSA Data 1");
	    data.put("Key-2", "JSA Data 2");
	 
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