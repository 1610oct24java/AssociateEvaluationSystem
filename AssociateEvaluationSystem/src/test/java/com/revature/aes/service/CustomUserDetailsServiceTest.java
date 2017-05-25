package com.revature.aes.service;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.revature.aes.beans.Security;
import com.revature.aes.beans.User;
import com.revature.aes.dao.UserDAO;

public class CustomUserDetailsServiceTest {
	
	@Autowired
    UserService uService;
	
	@Autowired
	SecurityService sService;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testLoadUserByUsername() {
//		User user = uService.findUserByEmailIgnoreCase("trainers@revature.com");
//		Security security = sService.findSecurityByUserId(user.getUserId());
//		assertTrue(security.getUserId() > 0);
	}

	@Test
	public void testCheckForValidPassword() {
		return;
		// fail("Not yet implemented");
	}

	@Test
	public void testGetAuthorities() {
		return;
		// fail("Not yet implemented");
	}

	@Test
	public void testGetRoles() {
		return;
		// fail("Not yet implemented");
	}

	@Test
	public void testGetGrantedAuthorities() {
		return;
		// fail("Not yet implemented");
	}

}
