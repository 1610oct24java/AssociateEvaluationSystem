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
		AssessmentRequest ar = new AssessmentRequest();
		
		Properties prop = new PropertyReader().propertyRead("assessmentRequest.properties");
	
		CategoryRequest catReq = new CategoryRequest();
		CategoryRequest catReq2 = new CategoryRequest();
		CategoryRequest catReq3 = new CategoryRequest();
		CategoryRequest catReq4 = new CategoryRequest();

		catReq.setCategory(prop.getProperty("cat1"));
		catReq.setMcQuestions(Integer.parseInt(prop.getProperty("cat1mcQuestions")));
		catReq.setMsQuestions(Integer.parseInt(prop.getProperty("cat1msQuestions")));
		catReq.setDdQuestions(Integer.parseInt(prop.getProperty("cat1ddQuestions")));
		catReq.setCsQuestions(Integer.parseInt(prop.getProperty("cat1csQuestions")));

		catReq2.setCategory(prop.getProperty("cat2"));
		catReq2.setMcQuestions(Integer.parseInt(prop.getProperty("cat2mcQuestions")));
		catReq2.setMsQuestions(Integer.parseInt(prop.getProperty("cat2msQuestions")));
		catReq2.setDdQuestions(Integer.parseInt(prop.getProperty("cat2ddQuestions")));
		catReq2.setCsQuestions(Integer.parseInt(prop.getProperty("cat2csQuestions")));
		
		catReq3.setCategory(prop.getProperty("cat3"));
		catReq3.setMcQuestions(Integer.parseInt(prop.getProperty("cat3mcQuestions")));
		catReq3.setMsQuestions(Integer.parseInt(prop.getProperty("cat3msQuestions")));
		catReq3.setDdQuestions(Integer.parseInt(prop.getProperty("cat3ddQuestions")));
		catReq3.setCsQuestions(Integer.parseInt(prop.getProperty("cat3csQuestions")));
		
		catReq4.setCategory(prop.getProperty("cat4"));
		catReq4.setMcQuestions(Integer.parseInt(prop.getProperty("cat4mcQuestions")));
		catReq4.setMsQuestions(Integer.parseInt(prop.getProperty("cat4msQuestions")));
		catReq4.setDdQuestions(Integer.parseInt(prop.getProperty("cat4ddQuestions")));
		catReq4.setCsQuestions(Integer.parseInt(prop.getProperty("cat4csQuestions")));

		//For a future sprint this is where you would set the time limit
		List<CategoryRequest> list = new ArrayList<CategoryRequest>();

		list.add(catReq);
		list.add(catReq2);
		list.add(catReq3);
		list.add(catReq4);

		ar.setCategoryRequestList(list);
		ar.setTimeLimit(Integer.parseInt(prop.getProperty("timelimit")));

		return ar;
	}

	public String loadAddress(){
		
		Properties prop = new PropertyReader().propertyRead("ipConfig.properties");
		
		return prop.getProperty("home");
	}
}
