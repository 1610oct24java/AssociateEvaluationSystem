package com.revature.aes.locator;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.logging.Logging;
import com.revature.aes.util.PropertyReader;

/**
 * 
 * This service locator class provides a single location to send Rest calls
 * to the Bank team's code. 
 * 
 * @author Michelle Slay
 *
 */

@Service
public class AssessmentServiceLocatorImpl implements AssessmentServiceLocator {

	@Autowired
	PropertyReader propertyReader;
	
	@Autowired
	Logging log;

	private static String URI;
	private static final String URIExt = "/aes";
	private RestTemplate restTemplate = new RestTemplate();

	@Inject
	private org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;

	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private void configureRestService(){
		
		String ip;
		int port = serverProperties.getPort();

		try{

			ip = InetAddress.getLocalHost().getHostAddress();

		} catch (UnknownHostException e) {
			log.error("Failed to set localhost address to ip const");
			log.stackTraceLogging(e);
			ip = "localhost";
		}

		URI = ip + ":" + port;

	}

	

	/*@PostConstruct
	protected void postConstruct(){

		URI = propertyReader.propertyRead().getProperty("home")+URIExt;

	}
*/

	
	/**
	 * 
	 * This method sends a rest call to the bank team's code.
	 * There the link to the generated assessment is returned
	 * in the response. 
	 * 
	 * @param AssessmentRequest
	 * @return AssessmentRequest
	 */
	@Override
	public AssessmentRequest getLink(AssessmentRequest request) {
		//Change the URL to whatever Matthew's thingy will be <-- Descriptive comments are overrated.
		String lines = "=============================================";
		log.debug(lines);
		log.debug(request.toString());
		log.debug(lines);	
		log.debug("CALLING MATTHEWS SERVICE!");
		ResponseEntity<AssessmentRequest> responseEntity = restTemplate.postForEntity("http://"+URI+URIExt+"/user/RandomAssessment", request, AssessmentRequest.class);

		AssessmentRequest response = responseEntity.getBody();
		log.debug(lines);
		log.debug(response.toString());
		log.debug(lines);
		HttpStatus httpStatus = responseEntity.getStatusCode();
		
		if(httpStatus != HttpStatus.OK)
			return null;
		
		return response;
	}
}
