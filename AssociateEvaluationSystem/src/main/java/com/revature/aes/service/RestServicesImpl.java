package com.revature.aes.service;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.User;
import com.revature.aes.loader.AssessmentRequestLoader;
import com.revature.aes.locator.AssessmentServiceLocator;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class RestServicesImpl implements RestServices {

	private Logging log = new Logging();

	@Autowired
	private AssessmentServiceLocator assessmentService;
	@Autowired
	private AssessmentAuthService authService;

	@Inject
	private org.springframework.boot.autoconfigure.web.ServerProperties serverProperties;

	private static int port;

	private static String ip;

	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private void configureRestService(){

		port = serverProperties.getPort();

		try{

			ip = InetAddress.getLocalHost().getHostAddress();

		} catch (UnknownHostException e) {
			log.error("Failed to set localhost address to ip const");
			ip = "localhost";
		}

	}

	@Override
	public String finalizeCandidate(User candidate, String pass) {
		AssessmentRequestLoader loader = new AssessmentRequestLoader();
//		Map<String,String> map = new HashMap<>();
		int userId = candidate.getUserId();
//		String email = candidate.getEmail();
		String category = candidate.getFormat();
		
		AssessmentRequest ar = loader.loadRequest(category);
		
		ar.setUserEmail(candidate.getEmail());
		
		ar = assessmentService.getLink(ar);
		
		String link = ar.getLink();
		
		AssessmentAuth auth = new AssessmentAuth();
		auth.setUrlAssessment(link);
		auth.setUrlAuth("http://" + ip + ":" + port + "/aes");
		auth.setUserId(userId);
		
		authService.save(auth);
		
//		map.put("email", email);
//		map.put("link", auth.getUrlAuth());
//		map.put("pass", pass);
		return auth.getUrlAuth();
	}
}
