package com.revature.aes.dao;

import com.revature.aes.beans.AssessmentAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is a repository for retrieving AssessmentAuths 
 *	from the database.
 * 
 * @author Michelle Slay 
 */
@Repository
public interface AssessmentAuthDao extends JpaRepository<AssessmentAuth, Integer> {
	
	/**
	 * If you need to retrieve an assessment by the id of the
	 * user associated with it this is the method for you.
	 * 
	 * @param userId
	 * 		id of the user associated with the assessmentauth
	 * @return
	 * 		assessmentauth associated with that user
	 */
	public AssessmentAuth findByUserId(int userId);
}