package com.revature.aes.loader;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.logging.Logging;
import com.revature.aes.util.PropertyReader;

@Component
public class AssessmentRequestLoader {

	@Autowired
	private Logging log;

	public AssessmentRequest loadRequest(String category){
		
		AssessmentRequest ar = new AssessmentRequest();
		
		Properties prop = new PropertyReader().propertyRead("assessmentRequest.properties");
		
		ar.setCategory(category);
		ar.setMcQuestions(Integer.parseInt(prop.getProperty("mcQuestions")));
		ar.setMsQuestions(Integer.parseInt(prop.getProperty("msQuestions")));
		ar.setDdQuestions(Integer.parseInt(prop.getProperty("ddQuestions")));
		ar.setCsQuestions(Integer.parseInt(prop.getProperty("csQuestions")));
		//For a future sprint this is where you would set the time limit
		
		return ar;
	}

	public String loadAddress(){
		
		Properties prop = new PropertyReader().propertyRead("ipConfig.properties");
		
		return prop.getProperty("home");
	}
}
