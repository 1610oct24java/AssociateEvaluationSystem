package com.revature.aes.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.aes.beans.Role;
import com.revature.aes.beans.User;

public class CustomUserDetailsServiceTest {

	@Before
	public void setUp() throws Exception {
		User user = new User();
		user.setFirstName("trainer");
		user.setLastName("admin");
		user.setEmail("trainers@revature.com");
		Role userRole = new Role();
		userRole.setRoleId(1);
		user.setRole(userRole);
	}

	@Test
	public void testLoadUserByUsername() {
		return;
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCheckForValidPassword() {
		return;
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetAuthorities() {
		return;
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRoles() {
		return;
		// fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetGrantedAuthorities() {
		return;
		// fail("Not yet implemented"); // TODO
	}

}
