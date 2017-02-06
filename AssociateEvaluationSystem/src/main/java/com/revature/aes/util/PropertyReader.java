package com.revature.aes.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class PropertyReader {
	
	public Properties propertyRead(String propertiesFilename) {
		
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(propertiesFilename);
		
		System.out.println("Input stream: " + inputStream);
		Properties prop = new Properties();
		
		// load the inputStream using the Properties
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t"
					+ thing.getLineNumber()
					+ "\nin Method:\t"
					+ thing.getMethodName()
					+ "\nin Class:\t"
					+ thing.getClassName(), e);
		}
		
		return prop;
	}
	
}
