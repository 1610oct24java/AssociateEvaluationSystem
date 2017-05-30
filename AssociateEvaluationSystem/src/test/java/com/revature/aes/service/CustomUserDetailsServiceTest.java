package com.revature.aes.service;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetailsServiceTest {
	String username = "trainers@revature.com";
	String password = "password";	
	List<GrantedAuthority> authorities = new ArrayList<>();
	List<String> role = new ArrayList<>();
	
	@Before
	public void init() {
		role.add("ROLE_ADMIN");
		for (String r : role) {
            authorities.add(new SimpleGrantedAuthority(r));
		}
	}

	@Test
	public void testLoadUserByUsername() {
		new User(username, password, authorities);
	}

}
