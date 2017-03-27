package com.revature.aes.grading;

import com.revature.aes.util.PropertyReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.revature.aes.logging.Logging;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component("snippetEvaluationClient")
public class SnippetEvaluationClient {
	
	@Autowired
	Logging log;

	@Autowired
	private PropertyReader propertyReader;

	//@Value("${hulqBASH.endpoint}")
    private static String hulqRESTlocation;// = "http://ec2-54-203-115-7.us-west-2.compute.amazonaws.com:8080";

	@PostConstruct
	protected void postConstruct() {

		configureHulqREST();

	}

	private void configureHulqREST() {

		Properties properties = propertyReader.propertyRead("ipConfig.properties");

		hulqRESTlocation = properties.getProperty("hulqBASH");

	}

	public double evaluateSnippet(String submissionKey, String solutionKey) {

		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			log.info("hulqBash endpoint: " + hulqRESTlocation);
			HttpPost request = new HttpPost(hulqRESTlocation + "/submission");
			StringEntity params = new StringEntity("{\"submission\":\"" + submissionKey + "\",\"solution\":\"" + solutionKey + "\"} ");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println("Source: " + submissionKey + " [grade: " + responseString + " ]");
			return Double.parseDouble(responseString)/100;

		} catch (Exception e) {
			log.stackTraceLogging(e);
			//e.printStackTrace();
			return 1.0;
		}
	}
}