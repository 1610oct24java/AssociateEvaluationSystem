package com.revature.aes.encoder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.revature.aes.service.UserService;

public class MyEncoder {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		System.out.println(encoder.encode("asd"));
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/beans.xml");
		UserService service =(UserService) ac.getBean("userServiceImpl");

		System.out.println(service.findAllUsers());
		
		System.out.println(service.findUserByEmail("asd@gmail.com"));
	}
}
