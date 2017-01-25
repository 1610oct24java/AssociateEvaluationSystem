package com.revature.aes.loader;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class AssessmentRequestLoader {

	@Autowired
	private Logging log;

	public AssessmentRequest loadRequest(String category){
		AssessmentRequest ar = new AssessmentRequest();
		Properties prop = new Properties();
		
		try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("assessmentRequest.properties")){
			prop.load(input);
		} catch (IOException e) {
			// Close the inputStream
			log.error(e.toString());
			return null;
		}
		
		ar.setCategory(category);
		ar.setMcQuestions(Integer.parseInt(prop.getProperty("mcQuestions")));
		ar.setMsQuestions(Integer.parseInt(prop.getProperty("msQuestions")));
		ar.setDdQuestions(Integer.parseInt(prop.getProperty("ddQuestions")));
		ar.setCsQuestions(Integer.parseInt(prop.getProperty("csQuestions")));
		//For a future sprint this is where you would set the time limit
		
		return ar;
	}

	public String loadAddress(){
		Properties prop = new Properties();
		
		try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("ipConfig.properties")){
			prop.load(input);
		} catch (IOException e) {
			// Close the inputStream
			log.error(e.toString());
			return null;
		}
		
		return prop.getProperty("home");
	}
}
