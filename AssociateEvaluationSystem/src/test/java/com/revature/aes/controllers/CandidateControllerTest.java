package com.revature.aes.controllers;

//import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.revature.aes.service.AssessmentAuthService;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
//@WebMvcTest(CandidateController.class)
@WebAppConfiguration
@SpringBootTest(classes = WebApplicationContext.class)
public class CandidateControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private AssessmentAuthService authService;
	
	@Before
	public void setUp() throws Exception {
		this.mvc = webAppContextSetup(webApplicationContext).build(); 
		}


	@Test
	public void testGetLink() throws Exception {
		String email = "whocares@5.emailfake.ml";
		
		mvc.perform(get("/candidate/" + email + "/link").accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		
	}

}
