package com.revature.aes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.AssessmentAuth;

@Repository
public interface AssessmentAuthDao extends JpaRepository<AssessmentAuth, Integer> {
	
}