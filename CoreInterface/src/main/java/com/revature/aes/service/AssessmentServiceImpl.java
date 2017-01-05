package com.revature.aes.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDao;

@Service
public class AssessmentServiceImpl implements AssessmentService {
	Logger log = Logger.getRootLogger();
	
	@Autowired
	private AssessmentDao dao;

	@Override
	public Assessment findByUser(User user) {
		// 
		return dao.findByUser(user);
	}
	
	@Override
	public Integer findGradeByUser(User user){
		Assessment asmt = dao.findByUser(user);
		log.info(asmt);
		if(asmt != null)
			return dao.findByUser(user).getGrade();
		else return null;
	}

	@Override
	public List<User> findUsersWithNoGrade() {
		// 
		ArrayList<User> users = new ArrayList<>();
		
		List<Assessment> gradlessAssessments = dao.findByGradeIsNull();
		int oneWeekInMillis = 7 * 24 * 60 * 60 * 1000;
		int tenSecondGracePeriod = 10000;
		oneWeekInMillis += tenSecondGracePeriod;
		
		for(Assessment a : gradlessAssessments){
			if(a.getCreatedTimeStamp().getTime() > oneWeekInMillis)
				users.add(a.getUser());
		}
		
		return users;
	}
}