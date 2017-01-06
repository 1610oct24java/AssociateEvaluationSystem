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

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private RestTemplate restTemplate;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		// ==============Make post to /login=====================
		
		try {
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpPost postRequest = new HttpPost("rest/users");
			
			// HttpPost postRequest =
			// new HttpPost("http://localhost:1993/core/login");
			
			StringEntity input = new StringEntity("{\"params\":{\"username\":"
					+ name
					+ ",\"password\":\""
					+ password
					+ "\"}}");
			
			input.setContentType("application/json");
			
			postRequest.setEntity(input);
			postRequest.addHeader("Content-Type", "application/json");
			
			HttpResponse response = httpClient.execute(postRequest);
			
			System.out.println(
					"Status code: " + response.getStatusLine().getStatusCode());
			
			// =============Make get to /auth===========================
			
			httpClient = HttpClientBuilder.create().build();
			
			// HttpGet getRequest =
			// new HttpGet("http://localhost:1993/core/security/auth");
			
			HttpGet getRequest = new HttpGet("rest/users");
			
			HttpResponse responseGet = httpClient.execute(getRequest);
			
			System.out.println("Status code: "
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
		User[] user = null;
		
		restTemplate = new RestTemplate();
		
		// user = restTemplate.getForObject(
		// "http://localhost:1993/core/security/auth", User[].class);
		
		user = restTemplate.getForObject("rest/users", User[].class);
		
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
