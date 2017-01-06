package com.revature.aes.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
	
	public Properties propertyRead() {
		
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("resources/ipconfig.properties");
		
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
