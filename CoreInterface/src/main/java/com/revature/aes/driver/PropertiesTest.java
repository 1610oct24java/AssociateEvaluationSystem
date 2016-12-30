package com.revature.aes.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class PropertiesTest {
	@Test
	public void testProperties(){
		Properties prop = new Properties();
		
		try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("AssessmentRequest.properties")){
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println(prop.getProperty("category"));
		System.out.println(prop.getProperty("mcQuestions"));
		System.out.println(prop.getProperty("msQuestions"));
		System.out.println(prop.getProperty("ddQuestions"));
		System.out.println(prop.getProperty("csQuestions"));
		System.out.println(prop.getProperty("timelimit"));
	}
}
