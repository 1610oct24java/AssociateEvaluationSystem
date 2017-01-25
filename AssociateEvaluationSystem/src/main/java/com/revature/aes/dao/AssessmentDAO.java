package com.revature.aes.dao;

import com.revature.aes.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.aes.beans.Assessment;

import java.util.List;

@Repository
public interface AssessmentDAO extends JpaRepository<Assessment, Integer> {
	
	public Assessment findAssessmentByAssessmentId(int id);
	//public void saveAssessmentBy(Assessment newAssessment);
	/**
	 * This method is necessary to find all the assessments that
	 * haven't been graded.
	 *
	 * @return
	 * A list of ungraded assessments
	 */
	public List<Assessment> findByGradeIsNull();
	/**
	 * This method finds an assessment associated
	 * with that particular user.
	 *
	 * @param usr
	 * The user that an assessment is associated
	 * with.
	 *
	 * @return
	 * The assessment associated with that user
	 */
	public Assessment findByUser(User usr);
	/**
	 * This method finds the grade that a particular
	 * user got on a given assessment.
	 *
	 * @param user
	 * 		The candidate whose grade you're searching
	 * for
	 * @return
	 * 		The grade you're looking for
	 */
	public Integer findGradeByUser(User user);
}
