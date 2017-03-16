package com.revature.aes.grading;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.logging.Logging;

@Service
public class CoreEmailClient {

	@Autowired
	Logging log;

	// does 
	private String serviceHost;

	public CoreEmailClient(String serviceHost) {
		super();
		this.serviceHost = serviceHost;
	}
	
	public CoreEmailClient() {
	}

	public void setServiceHost(String serviceHost) {
		this.serviceHost = serviceHost;
	}

	public boolean sendEmailAfterGrading(String emailAddress, int assessmentId) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost(serviceHost + "/user/" + emailAddress + "/mail");
			log.info("Posting to email service at: " + serviceHost + "user/" + emailAddress + "/mail");
			StringEntity params = new StringEntity(
					"{\"link\":\"" 
					+ "\",\"tempPass\":\"" 
					+ "\",\"type\":\"" + "candidateCompleted" 
					+ "\",\"assessmentId\":\"" + assessmentId + "\"} ");
			log.info("Sending message body: " + "{\"link\":\""
					+ "\",\"tempPass\":\""
					+ "\",\"type\":\"" + "candidateCompleted"
					+ "\",\"assessmentId\":\"" + assessmentId + "\"} ");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			httpClient.execute(request);
			return true;

		} catch (Exception e) {
			log.stackTraceLogging(e);
			return false;
		}
	}
}