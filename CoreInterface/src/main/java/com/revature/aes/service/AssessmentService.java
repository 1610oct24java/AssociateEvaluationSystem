package com.revature.aes.service;

import java.util.List;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;

public interface AssessmentService {
	public List<User> findUsersWithNoGrade();
	public Assessment findByUser(User user);
	Integer findGradeByUser(User user);
}
