package com.revature.aes.service;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.User;
import com.revature.aes.config.IpConf;
import com.revature.aes.loader.AssessmentRequestLoader;
import com.revature.aes.locator.AssessmentServiceLocator;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;;

@Service
public class RestServicesImpl implements RestServices {

	private Logging log = new Logging();

	@Autowired
	private AssessmentServiceLocator assessmentService;
	@Autowired
	private AssessmentAuthService authService;

	@Autowired
	private IpConf ipConf;

	private static String ip;

	@PostConstruct
	protected void postConstruct(){

		configureRestService();

	}

	private void configureRestService(){

		ip = ipConf.getHostName();

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
		auth.setUrlAuth("http://" + ip + "/aes");
		auth.setUserId(userId);
		
		authService.save(auth);
		
//		map.put("email", email);
//		map.put("link", auth.getUrlAuth());
//		map.put("pass", pass);
		return auth.getUrlAuth();
	}
}
