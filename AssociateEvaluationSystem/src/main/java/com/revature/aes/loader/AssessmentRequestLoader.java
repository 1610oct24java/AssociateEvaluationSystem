package com.revature.aes.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.logging.Logging;
import com.revature.aes.util.PropertyReader;

@Component
public class AssessmentRequestLoader {

	@Autowired
	private Logging log;

	public AssessmentRequest loadRequest(String category){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("INSIDE ASSESSMENT REQUEST LOADER");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		AssessmentRequest ar = new AssessmentRequest();
		
		Properties prop = new PropertyReader().propertyRead("assessmentRequest.properties");
		
		CategoryRequest catReq = new CategoryRequest();
		CategoryRequest catReq2 = new CategoryRequest();
		
		////////////////////////////////////////////////////////////////////////////////
		//catReq.setCategory(category); //This is the category chosen by the recruiter
		//Not sure what to do with this the way we have this set up now
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("LOAD CAT1");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		catReq.setCategory(prop.getProperty("cat1"));
		catReq.setMcQuestions(Integer.parseInt(prop.getProperty("cat1mcQuestions")));
		catReq.setMsQuestions(Integer.parseInt(prop.getProperty("cat1msQuestions")));
		catReq.setDdQuestions(Integer.parseInt(prop.getProperty("cat1ddQuestions")));
		catReq.setCsQuestions(Integer.parseInt(prop.getProperty("cat1csQuestions")));
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("LOAD CAT2");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		catReq2.setCategory(prop.getProperty("cat2"));
		catReq2.setMcQuestions(Integer.parseInt(prop.getProperty("cat2mcQuestions")));
		catReq2.setMsQuestions(Integer.parseInt(prop.getProperty("cat2msQuestions")));
		catReq2.setDdQuestions(Integer.parseInt(prop.getProperty("cat2ddQuestions")));
		catReq2.setCsQuestions(Integer.parseInt(prop.getProperty("cat2csQuestions")));
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("ADD CATS TO LIST");
		System.out.println(catReq);
		System.out.println(catReq2);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		
		//For a future sprint this is where you would set the time limit
		System.out.println("MAKING LIST");
		List<CategoryRequest> list = new ArrayList<CategoryRequest>();
		System.out.println("ADDING CAT1");
		list.add(catReq);
		System.out.println("ADDING CAT2");
		list.add(catReq2);
		ar.setCategoryRequestList(list);
		System.out.println("ASSESSMENT REQUEST BEFORE TIME" + ar);
		ar.setTimeLimit(Integer.parseInt(prop.getProperty("timelimit")));
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("ASSESSMENT REQUEST " + ar);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		return ar;
	}

	public String loadAddress(){
		
		Properties prop = new PropertyReader().propertyRead("ipConfig.properties");
		
		return prop.getProperty("home");
	}
}
