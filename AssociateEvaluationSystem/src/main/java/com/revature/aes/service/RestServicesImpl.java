package com.revature.aes.service;

import javax.annotation.PostConstruct;

import com.revature.aes.beans.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.config.IpConf;
import com.revature.aes.loader.AssessmentRequestLoader;
import com.revature.aes.locator.AssessmentServiceLocator;
import javax.annotation.PostConstruct;;

@Service
public class RestServicesImpl implements RestServices {

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
	protected void postConstruct(){

		configureRestService();

	}

	private void configureRestService(){

		ip = ipConf.getHostName();

	}

	@Override
	public String finalizeCandidate(User candidate, String pass) {
		
//		AssessmentRequestLoader loader = new AssessmentRequestLoader();
		int userId = candidate.getUserId();
		String category = candidate.getFormat();
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("RestServicesImpl.java at the part that chelle wrote.");
		AssessmentRequest ar = assReqServ.getAssessmentRequestTemplate(); //assReqServ.getassessment by id here
		System.out.println(ar);
		
		ar.setUserEmail(candidate.getEmail());

		for(CategoryRequest categoryRequest : ar.getCategoryRequestList()){

			if(categoryRequest.getCategory().getName().equalsIgnoreCase("core language")){

				categoryRequest.setCategory(categoryService.getCategoryByName(candidate.getFormat()));

			}

		}

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
