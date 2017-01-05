package com.revature.aes.dao;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.Format;

public interface IAssessmentService {
	public Assessment getAssessmentById(int id);
	public void saveAssessmentById();
}
