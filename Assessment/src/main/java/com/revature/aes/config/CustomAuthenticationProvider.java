package com.revature.aes.config;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.User;
import com.revature.aes.util.Error;
import com.revature.aes.logging.Logging;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	Logging log = new Logging();
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		String postRequestEndpoint = "rest/users";
		
		// ==============Make post to /login=====================
		
		try {
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpPost postRequest = new HttpPost(postRequestEndpoint);
			
			StringEntity input = new StringEntity("{\"params\":{\"username\":"
					+ name
					+ ",\"password\":\""
					+ password
					+ "\"}}");
			
			input.setContentType("application/json");
			
			postRequest.setEntity(input);
			postRequest.addHeader("Content-Type", "application/json");
			
			HttpResponse response = httpClient.execute(postRequest);
			
			log.info(
					"Status code: " + response.getStatusLine().getStatusCode());
			
			// =============Make get to /auth===========================
			
			httpClient = HttpClientBuilder.create().build();
			
			HttpGet getRequest = new HttpGet(postRequestEndpoint);
			
			HttpResponse responseGet = httpClient.execute(getRequest);
			
			log.info("Status code: "
					+ responseGet.getStatusLine().getStatusCode());
			
			// ===============Return user information==================
			
		} catch (Exception e) {
			
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t"
					+ thing.getLineNumber()
					+ "\nin Method:\t"
					+ thing.getMethodName()
					+ "\nin Class:\t"
					+ thing.getClassName(), e);
			
		}
		
		// =============================================================================
		
		RestTemplate restTemplate = new RestTemplate();
		
		User[] user = restTemplate.getForObject(postRequestEndpoint, User[].class);
		
		if (user != null) {
			
			return new UsernamePasswordAuthenticationToken(name, password,
					new ArrayList<>());
		}
		
		return null;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
