package com.revature.aes.locator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FAKEMailServiceLocator implements MailServiceLocator {
	Logger log = Logger.getRootLogger();

	@Override
	public boolean send(String email, String... contents) {
		// TODO Remove the @Service annotation when Wes's service is up
		
		log.info("Email: "+ email);
		for(String s : contents){
			log.info("\t" + s);
		}
		
		return true;
	}

}
