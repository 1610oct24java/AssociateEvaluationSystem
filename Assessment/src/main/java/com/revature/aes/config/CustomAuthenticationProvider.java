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
		
		System.out.println("From Assessment login");
		System.out.println("Name:" + name + " | password:" + password);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setRoleTitle("admin");
		
		User user = new User();
		user.setUserId(1);
		user.setEmail("email@email.com");
		user.setFirstName("First");
		user.setLastName("Last");
		user.setSalesforce(1);
		user.setRecruiterId(1);
		user.setRole(role);
		user.setDatePassIssued("01/01/2001");
		
		// User user = null;
		// try {
		// user = restTemplate.getForObject(
		// "http://192.168.60.64:1993/core/user", User.class);
		// // rest call
		// System.out.println(user.toString());
		// } catch (Exception e) {
		// System.out.println("User is null");
		// StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
		// Error.error("\nat Line:\t"
		// + thing.getLineNumber()
		// + "\nin Method:\t"
		// + thing.getMethodName()
		// + "\nin Class:\t"
		// + thing.getClassName(), e);
		//
		// user = new User();
		// user.setUserId(1);
		// user.setFirstName("First");
		// user.setLastName("Last");
		// user.setEmail("email@email.com");
		// user.setSalesforce(1);
		// user.setRecruiterId(1);
		// user.setRole(null);
		// user.setDatePassIssued("01/01/2017");
		// }
		
		if (user != null) {
			
			System.out.println("User is not null");
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
