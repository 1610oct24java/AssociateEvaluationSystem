package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.aes.beans.Assessment;

@Repository
public interface AssessmentDao extends JpaRepository<Assessment, Integer>{
	public Assessment findAssesmentById(String id);
}