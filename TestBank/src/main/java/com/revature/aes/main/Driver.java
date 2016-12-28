package com.revature.aes.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.aes.beans.Format;
import com.revature.aes.daos.QuestionDAO;
import com.revature.aes.services.QuestionService;

public class Driver {
	
	public static void main(String args[]){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("/WEB-INF/beans.xml");
		QuestionService service = (QuestionService) ac.getBean("questionServiceImpl");
		Format format = new Format();
		
		System.out.println(service.getAllQuestions());
		
	}
	
}


