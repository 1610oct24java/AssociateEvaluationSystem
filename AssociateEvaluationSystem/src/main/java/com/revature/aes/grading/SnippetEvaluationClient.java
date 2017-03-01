package com.revature.aes.grading;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class SnippetEvaluationClient {
	public double evaluateSnippet(String submissionKey, String solutionKey) {
		private static String hulqRESTlocation = "http://ec2-54-203-115-7.us-west-2.compute.amazonaws.com:8080";
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost(hulqRESTlocation + "/submission);
			StringEntity params = new StringEntity("{\"submission\":\"" + submissionKey + "\",\"solution\":\"" + solutionKey + "\"} ");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println("Source: " + submissionKey + " [grade: " + responseString + " ]");
			return Double.parseDouble(responseString)

		} catch (Exception e) {
			return 1.0;
		}
	}
}