package com.revature.aes.grading;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.revature.aes.util.Error;

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
			StringEntity params = new StringEntity("{\"link\":\"" + "\",\"tempPass\":\"" + "\",\"type\":\""
					+ "candidateCompleted" + "\",\"assessmentId\":\"" + assessmentId + "\"} ");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			httpClient.execute(request);
			return true;

		} catch (Exception e) {
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t" + thing.getLineNumber() + "\nin Method:\t" + thing.getMethodName()
					+ "\nin Class:\t" + thing.getClassName(), e);
			return false;
		}
	}
}