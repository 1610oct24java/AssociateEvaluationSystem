package com.revature.aes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.aes.beans.Assessment;

public interface AssessmentDao extends JpaRepository<Assessment, Integer> {
	public List<Assessment> findByGradeIsNull();
}
