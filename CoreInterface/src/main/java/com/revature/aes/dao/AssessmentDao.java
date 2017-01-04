package com.revature.aes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.User;

@Repository
public interface AssessmentDao extends JpaRepository<Assessment, Integer>{
	public List<Assessment> findByGradeIsNull();
	public Assessment findAssesmentByUser(User id);
}