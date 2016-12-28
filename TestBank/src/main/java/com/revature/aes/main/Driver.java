package com.revature.aes.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.aes.beans.Format;
import com.revature.aes.daos.QuestionDAO;
import com.revature.aes.services.QuestionService;

public class Driver {
	
	public static void main(String args[]){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		QuestionService service = (QuestionService) ac.getBean("questionServiceImpl");
		
		System.out.println(service.getAllQuestions());
		
	}
	
}


