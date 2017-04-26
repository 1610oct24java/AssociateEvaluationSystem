package com.revature.aes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDAO;
import com.revature.aes.grading.AssessmentGrader;
import com.revature.aes.logging.Logging;

@Service
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
	Logging log;

	@Autowired
	AssessmentDAO assDAO;

	@Autowired
	AssessmentGrader assessmentGrader;
	
	@Override
	public Assessment getAssessmentById(int id) {
		Assessment out = assDAO.findAssessmentByAssessmentId(id);
		return out;
	}

	@Override
	public Assessment saveAssessment(Assessment assessment) {
		return assDAO.save(assessment);
	}
	
	@Override
	public void updateAssessment(Assessment assessment) {
		Assessment oldAssessment = assDAO.findOne(assessment.getAssessmentId());
		//oldAssessment = assessment;
		assDAO.save(oldAssessment);
	}

	@Override
	public List<Assessment> findByUser(User user) {
		return (List<Assessment>)assDAO.findByUser(user);
	}

	@Override
	public List<Assessment> findAssessmentsByUser(User user) {
		return (List<Assessment>)assDAO.findAssessmentsByUser(user);
	}

	@Override
	public Integer findGradeByUser(User user) {
		List<Assessment> asmt = assDAO.findAssessmentsByUser(user);
		log.info(asmt.toString());
		if (!asmt.isEmpty()) {
			Assessment as = Collections.max(asmt, Comparator.comparing(a -> a.getFinishedTimeStamp(), Comparator.nullsFirst(Comparator.naturalOrder())));
			return as.getGrade();
		}else {
			return null;
		}
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
		assessment.setGrade((int) Math.round(assessmentGrader.gradeAssessment(assessment)));
	}

	@Override
	public void deleteAssessment(Assessment assessment) {
		assDAO.delete(assessment);
	}

	@Override
	public List<Assessment> findAll() {
		// TODO Auto-generated method stub
		return assDAO.findAll();
	}
}
