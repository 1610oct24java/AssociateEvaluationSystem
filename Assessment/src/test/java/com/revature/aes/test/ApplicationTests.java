package com.revature.aes.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.revature.aes.Application.class)
@AutoConfigureMockMvc
public class ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Ignore
	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login =
				formLogin().user("user").password("password");
		
		mockMvc.perform(login).andExpect(authenticated().withUsername("user"));
	}
}
