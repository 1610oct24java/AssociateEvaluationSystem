package com.revature.aes.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
		//For a future sprint this is where you would set the time limit
		
		return ar;
	}

	public Map<String, String> loadAddresses(){
		Map<String, String> ipProps = new HashMap<>();
		Properties prop = new Properties();
		
		try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("ipConfig.properties")){
			prop.load(input);
		} catch (IOException e) {
			// Close the inputStream
			log.error(e);
			return null;
		}
		
		ipProps.put("core", prop.getProperty("core"));
		ipProps.put("asmt", prop.getProperty("asmt"));
		ipProps.put("bank", prop.getProperty("bank"));
		ipProps.put("aes",  prop.getProperty("aes"));
		
		return ipProps;
	}
}