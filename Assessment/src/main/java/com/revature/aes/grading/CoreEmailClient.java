package com.revature.aes.grading;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class CoreEmailClient {

	// does 
	private String serviceHost;

	public CoreEmailClient(String serviceHost) {
		super();
		this.serviceHost = serviceHost;
	}

	public boolean sendEmailAfterGrading(String emailAddress, int assessmentId) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost(serviceHost + "/user/" + emailAddress + "/mail");
			StringEntity params = new StringEntity(
					"{\"link\":\"" 
					+ "\",\"tempPass\":\"" 
					+ "\",\"type\":\"" + "candidateCompleted" 
					+ "\",\"assessmentId\":\"" + assessmentId + "\"} ");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			httpClient.execute(request);
			return true;

		} catch (Exception e) {
			return false;
		}
	}
}