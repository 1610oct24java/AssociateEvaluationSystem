package com.revature.aes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import com.revature.aes.dao.AssessmentDao;

@Service
public class AssessmentServiceImpl implements AssessmentService {
	@Autowired
	private AssessmentDao dao;

	@Override
	public List<User> findUsersWithNoGrade() {
		// 
		ArrayList<User> users = new ArrayList<>();
		
		List<Assessment> gradlessAssessments = dao.findByGradeIsNull();
		
		for(Assessment a : gradlessAssessments){
			if(a.getCreatedTimeStamp())
		}
		
		return users;
	}

}
