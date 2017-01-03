package com.revature.aes.beans;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.locator.MailServiceLocator;
import com.revature.aes.service.AssessmentService;

@Component
public class Scheduler {

	Logger log = Logger.getRootLogger();
	
	@Autowired
	private AssessmentService aService;
	@Autowired
	private MailServiceLocator locator;
	
	public void run(){
		// 
		log.debug("Entered scheduled task");
		List<User> users = aService.findUsersWithNoGrade();
		
		for(User u : users){
			locator.overdueAlert(u.getEmail());
		}
		log.debug("found " + users.size() + " unfinished assessments");
	}
}