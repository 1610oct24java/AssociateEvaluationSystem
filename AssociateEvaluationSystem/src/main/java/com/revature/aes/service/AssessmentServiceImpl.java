package com.revature.aes.service;

import com.revature.aes.beans.User;
import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.dao.AssessmentDAO;
import com.revature.aes.grading.AssessmentGrader;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
	Logging log;

	@Autowired
	AssessmentDAO assDAO;
	
	@Override
	public Assessment getAssessmentById(int id) {
		System.out.println("I'm servicing things! Poorly!");
		Assessment out = assDAO.findAssessmentByAssessmentId(id);
		return out;
	}

	@Override
	public Assessment saveAssessment(Assessment assessment) {
		System.out.println("I am saving things!! maybe?");
		return assDAO.save(assessment);
	}
	
	@Override
	public void updateAssessment(Assessment assessment) {
		Assessment oldAssessment = assDAO.findOne(assessment.getAssessmentId());
		oldAssessment = assessment;
		assDAO.save(oldAssessment);
	}

	@Override
	public Assessment findByUser(User user) {
		return assDAO.findByUser(user);
	}

	@Override
	public Integer findGradeByUser(User user) {
		Assessment asmt = assDAO.findByUser(user);
		log.info(asmt.toString());
		if(asmt != null)
			return assDAO.findByUser(user).getGrade();
		else return null;
	}

	@Override
	public List<User> findUsersWithNoGrade() {

		ArrayList<User> users = new ArrayList<>();

		List<Assessment> gradlessAssessments = assDAO.findByGradeIsNull();
		int oneWeekInMillis = 7 * 24 * 60 * 60 * 1000;
		int tenSecondGracePeriod = 10000;
		oneWeekInMillis += tenSecondGracePeriod;

		for(Assessment a : gradlessAssessments){
			if(a.getCreatedTimeStamp().getTime() > oneWeekInMillis)
				users.add(a.getUser());
		}

		return users;
	}


	public void gradeAssessment(Assessment assessment) {
		assessment.setGrade((int) Math.round(new AssessmentGrader().gradeAssessment(assessment)));
	}
}
