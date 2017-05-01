	package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;

public interface AssessmentService {
	public Assessment getAssessmentById(int id);
	
	public Assessment saveAssessment(Assessment assessment);
	
	public void updateAssessment(Assessment assessment);

	public List<Assessment> findByUser(User user);

	Integer findGradeByUser(User user);

	public List<User> findUsersWithNoGrade();

	public List<Assessment> findAssessmentsByUser(User user);
	
	public void deleteAssessment(Assessment assessment);
	
	public List<Assessment> findAll();

}
