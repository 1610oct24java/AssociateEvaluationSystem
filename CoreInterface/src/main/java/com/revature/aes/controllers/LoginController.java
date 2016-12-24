package com.revature.aes.controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.aes.beans.User;
import com.revature.aes.service.UserService;

@Controller
public class LoginController {

	private static User user = new User();
	static {
		user.setEmail("asd");
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String getLoginPage(ModelMap modelMap) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		UserService service =(UserService) ac.getBean("userServiceImpl");
		System.out.println(service.findUserByEmail("asd@gmail.com"));
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public User login(@RequestBody User user) {
		
		return user;
	}
}
