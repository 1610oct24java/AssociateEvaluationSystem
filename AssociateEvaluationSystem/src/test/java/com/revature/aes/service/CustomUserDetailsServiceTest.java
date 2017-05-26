package com.revature.aes.service;



import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomUserDetailsServiceTest {
	
	@Autowired
    UserService uService;
	
	@Autowired
	SecurityService sService;


	@Test
	public void testLoadUserByUsername() {
//		User user = uService.findUserByEmailIgnoreCase("trainers@revature.com");
//		Security security = sService.findSecurityByUserId(user.getUserId());
//		assertTrue(security.getUserId() > 0);
	}

	@Test
	public void testCheckForValidPassword() {
		return;
		
	}

	@Test
	public void testGetAuthorities() {
		return;
		
	}

	@Test
	public void testGetRoles() {
		return;
		
	}

	@Test
	public void testGetGrantedAuthorities() {
		return;
		
	}

}
