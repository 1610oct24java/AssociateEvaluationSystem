package com.revature.aes.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.revature.aes.beans.User;
import com.revature.aes.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService service;
	
	@RequestMapping(value="/",method = RequestMethod.GET)
	public String login() {
		return "index";
	}
	@RequestMapping(value="/home",method = RequestMethod.GET)
	public String getLoginPage(ModelMap modelMap, HttpSession session) {
		
//		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    String name = user.getUsername(); //get logged in username
//	    User currentUser = service.findUserByEmail(name);
//	    
//	    //direct user based on user
//	    String location = "";
//	    switch(currentUser.getRole().getRoleTitle().toLowerCase()) {
//	    case "recruiter":
//	    	location = "recruiterhome";
//	    	break;
//	    	
//	    case "candidate":
//	    	
//	    	//this will pull the link from the database to the candidate's assessment
//	    	break;
//	    	
//	    case "trainer":
//	    	location = "trainerhome";
//	    	break;
//	    	
//    	default:
//    		break;
//	    }
	    
//		return location;
		return "poopybutt";
	}

//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public ModelAndView login(
//		@RequestParam(value = "error", required = false) String error,
//		@RequestParam(value = "logout", required = false) String logout) {
//
//		ModelAndView model = new ModelAndView();
//		if (error != null) {
//			model.addObject("error", "Invalid username and password!");
//		}
//
//		if (logout != null) {
//			model.addObject("msg", "You've been logged out successfully.");
//		}
//		model.setViewName("login");
//
//		return model;
//
//	}
}