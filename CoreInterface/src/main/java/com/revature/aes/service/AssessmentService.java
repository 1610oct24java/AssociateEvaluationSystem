package com.revature.aes.service;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;

public interface AssessmentService {
	public Assessment findByUser(User user);
}
