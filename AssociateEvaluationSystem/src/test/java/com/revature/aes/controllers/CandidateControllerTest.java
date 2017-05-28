package com.revature.aes.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;



import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.service.AssessmentAuthService;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
//@ContextConfiguration("/test-config.xml")
//@WebMvcTest(CandidateController.class)
@SpringBootTest(classes = WebApplicationContext.class)
public class CandidateControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
//	@Autowired
	private MockMvc mvc;
	
//	@MockBean
	private AssessmentAuthService authService;
	
	
	@Before
	public void setUp() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build(); 
		}

	@Test
	public void testGetLink() throws Exception {
		String email = "whocares@5.emailfake.ml";
		
		given(authService.getLink(email)).willReturn(new AssessmentAuth(4350,260,"http://10.0.0.225:8090/aes", "http://192.168.60.108:8090/aes/quiz?asmt=5Aq"));
		
		mvc.perform(get("/candidate/"+ email + "/link", email).accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk()).andReturn();
		
		verify(authService.getLink(email));
		
	}

}
