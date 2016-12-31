package com.revature.aes.locator;

import org.springframework.stereotype.Service;

@Service
public class FAKEMailServiceLocator implements MailServiceLocator {

	@Override
	public boolean send(String email, String... contents) {
		// TODO Remove the @Service annotation when Wes's service is up
		
		System.out.println("Email: "+ email);
		for(String s : contents){
			System.out.println(s);
		}
		
		return true;
	}

}
