package com.revature.aes.controllers;

/**
 * Stand-alone Mockito JUnit test for the CandidateController (a Rest controller).
 * Can be modified to add additional testing of features specific
 * to this controller.  This test can be used as an example to set up testing
 * for other Rest controllers.
 * 
 * @author Amelia Hackworth
 * 
 * reference: http://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/
 */

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.service.AssessmentAuthService;

public class CandidateControllerTest {

	private MockMvc mockMvc;

	// creates a mock of the AssessmentAuthService.
	@Mock
	private AssessmentAuthService authService;

	AssessmentAuth assessmentAuth = new AssessmentAuth(2152, 136, "http://192.168.60.191:8090/aes",
			"http://192.168.60.85:8090/aes/quiz?asmt=319");

	// input for the rest controllers' @PathVariable
	String email = "ldclauss@svsu.edu";

	// injects the AssessmentAuthService into the candidate controller.
	@InjectMocks
	private CandidateController candidateController;

	// sets up the stand-alone testing for the CandidateController.
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();
	}

	// tests the response status and content type of the getLink method.
	@Test(expected = Exception.class)
	public final void testGetLink() throws Exception{
		
		when(authService.getLink(Mockito.anyString())).thenReturn(assessmentAuth);

		mockMvc.perform(get("/candidate/{email}/link", email)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

	}

}
