package com.revature.aes.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.User;
import com.revature.aes.config.IpConf;
import com.revature.aes.loader.AssessmentRequestLoader;
import com.revature.aes.locator.AssessmentServiceLocator;
import com.revature.aes.logging.Logging;

@Service
public class RestServicesImpl implements RestServices {

	private Logging log = new Logging();

	@Autowired
	private AssessmentServiceLocator assessmentService;
	@Autowired
	private AssessmentAuthService authService;
	
	@Autowired
	private AssessmentRequestService assReqServ;

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
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("RestServicesImpl.java at the part that chelle wrote.");
		AssessmentRequest ar = assReqServ.getAssessmentRequestTemplate(); //assReqServ.getassessment by id here
		System.out.println(ar);
		
		System.out.println("Is");
		System.out.println("this");
		System.out.println("a");
		System.out.println("code");
		System.out.println("smell");
		System.out.println("?");
		System.out.println("Can you find them all? Mwah hah hah hah!!!");
		
		ar.setUserEmail(candidate.getEmail());
		
		ar = assessmentService.getLink(ar);
		
		String link = ar.getLink();
		
		AssessmentAuth auth = new AssessmentAuth();
		auth.setUrlAssessment(link);
		auth.setUrlAuth("http://" + ip + "/aes");
		auth.setUserId(userId);
		
		authService.save(auth);
		
		return auth.getUrlAuth();
	}
}
