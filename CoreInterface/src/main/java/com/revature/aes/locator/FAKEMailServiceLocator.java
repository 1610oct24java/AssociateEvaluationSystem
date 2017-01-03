package com.revature.aes.locator;

import org.apache.log4j.Logger;

public class FAKEMailServiceLocator implements MailServiceLocator {
	Logger log = Logger.getRootLogger();

	@Override
	public boolean sendPassword(String email, String... contents) {
		log.info("Email: "+ email);
		for(String s : contents){
			log.info("\t" + s);
		}
		
		return true;
	}

	@Override
	public void overdueAlert(String email) {
		// 
		
	}

}
