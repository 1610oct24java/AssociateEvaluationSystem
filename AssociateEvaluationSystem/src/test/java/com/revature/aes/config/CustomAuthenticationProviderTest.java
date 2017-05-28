package com.revature.aes.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.revature.aes.beans.*;

public class CustomAuthenticationProviderTest extends AbstractJUnit4SpringContextTests {

	@After
	public void close() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testAuthenticate() throws Exception {
		User user = new User();
		user.setUserId(29);
		user.setEmail("trainers@revature.com");
		TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, null);
		SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
		assertThat(testingAuthenticationToken.isAuthenticated());
	}

}
