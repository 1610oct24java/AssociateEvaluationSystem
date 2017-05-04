package com.revature.aes.service;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.AssessmentAuth;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.beans.CategoryRequest;
import com.revature.aes.beans.User;
import com.revature.aes.config.IpConf;
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
	private CategoryService categoryService;

	@Autowired
	private IpConf ipConf;

	private static String ip;

	@PostConstruct
	protected void postConstruct() throws UnknownHostException{

		configureRestService();

	}

	private void configureRestService() throws UnknownHostException{

		ip = ipConf.getHostName();

	}

	@Override
	public String finalizeCandidate(User candidate, String pass) {
		
//		AssessmentRequestLoader loader = new AssessmentRequestLoader();
//		Map<String,String> map = new HashMap<>();
		int userId = candidate.getUserId();
//		String email = candidate.getEmail();
		String category = candidate.getFormat();
		AssessmentRequest ar = new AssessmentRequest(assReqServ.getAssessmentRequestTemplate()); //assReqServ.getassessment by id here
		//System.out.println(ar);

		ar.setUserEmail(candidate.getEmail());

		CategoryRequest oldCR = null;
		CategoryRequest newCR = null;

		for(CategoryRequest categoryRequest : ar.getCategoryRequestList()){

			if("core language".equalsIgnoreCase(categoryRequest.getCategory().getName())){

				newCR = new CategoryRequest(categoryRequest);
				newCR.setCategory(categoryService.getCategoryByName(category));

			}

		}

		ar.getCategoryRequestList().remove(oldCR);
		ar.getCategoryRequestList().add(newCR);

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
