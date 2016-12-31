package com.revature.aes.loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.AssessmentRequest;

@Component
public class AssessmentRequestLoader {
	private Logger log = Logger.getRootLogger();

	public AssessmentRequest loadRequest(String category){
		AssessmentRequest ar = new AssessmentRequest();
		Properties prop = new Properties();
		
		try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("assessmentRequest.properties")){
			prop.load(input);
		} catch (FileNotFoundException e) {
			// Close the inputStream
			log.error(e);
			return null;
		} catch (IOException e) {
			// Close the inputStream
			log.error(e);
			return null;
		}
		
		ar.setCategory(category);
		ar.setMcQuestions(Integer.parseInt(prop.getProperty("mcQuestions")));
		ar.setMsQuestions(Integer.parseInt(prop.getProperty("msQuestions")));
		ar.setDdQuestions(Integer.parseInt(prop.getProperty("ddQuestions")));
		ar.setCsQuestions(Integer.parseInt(prop.getProperty("csQuestions")));
		ar.setTimelimit(Integer.parseInt(prop.getProperty("timelimit")));
		
		return ar;
	}
}
