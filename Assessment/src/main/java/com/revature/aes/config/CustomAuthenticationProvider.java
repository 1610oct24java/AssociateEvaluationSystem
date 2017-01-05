package com.revature.aes.config;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.util.Error;
import com.revature.aes.beans.Role;
import com.revature.aes.beans.User;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private RestTemplate restTemplate;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User user = null;
		
		user = restTemplate.getForObject("http://192.168.60.64:1993/core/user",
				User.class);
		// rest call
		System.out.println(user.toString());
		
		if (user != null) {
			
			// use the credentials and authenticate against the third-party
			// system
			return new UsernamePasswordAuthenticationToken(name, password,
					new ArrayList<>());
		}
		
		return null;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	@RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "text/plain")
	public void process(@RequestBody String payload) throws Exception {
		
		System.out.println(payload);
		
	}
}
