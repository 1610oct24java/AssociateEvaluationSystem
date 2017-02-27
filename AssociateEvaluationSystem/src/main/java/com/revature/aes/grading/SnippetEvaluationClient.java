package com.revature.aes.grading;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class SnippetEvaluationClient {
	public int evaluateSnippet(String submissionKey, String solutionKey) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost("http://ec2-54-203-115-7.us-west-2.compute.amazonaws.com:8080/FileRunner/submit");
			StringEntity params = new StringEntity("{\"snippet\":\"" + submissionKey + "\",\"solution\":\"" + solutionKey + "\"} ");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println("Snippet: " + submissionKey + " [" + (Boolean.parseBoolean(responseString)? "pass" : "fail") + " ]");
			return Boolean.parseBoolean(responseString) ? 1 : 0;

		} catch (Exception e) {
			return 1;
		}
	}
}