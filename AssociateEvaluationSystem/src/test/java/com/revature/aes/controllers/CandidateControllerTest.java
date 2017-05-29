package com.revature.aes.controllers;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.service.AssessmentAuthService;


public class CandidateControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private AssessmentAuthService authService;
	
	AssessmentAuth assessmentAuth = new AssessmentAuth(4350,260,"http://10.0.0.225:8090/aes","http://192.168.60.108:8090/aes/quiz?asmt=5Aq");
	
	String email = "ldclauss@svsu.edu";
	
	@InjectMocks
	private CandidateController candidateController;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(candidateController)
				.build();
	}

	@Test
	public final void testGetLink() throws Exception {
		
		when(authService.getLink(Mockito.anyString())).thenReturn(assessmentAuth);
		mockMvc.perform(get("/candidate/{email}/link", email))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(null);
	}
	
	

}
