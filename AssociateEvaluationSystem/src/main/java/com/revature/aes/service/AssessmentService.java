package com.revature.aes.service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface AssessmentService {

	public Assessment getAssessmentById(int id);
	
	public Assessment saveAssessment(Assessment assessment);

	public void updateAssessment(Assessment assessment);

	public Assessment findByUser(User user);

	Integer findGradeByUser(User user);

	public List<User> findUsersWithNoGrade();

}
