package com.revature.aes.grading;

import java.util.ArrayList;
import java.util.Iterator;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.Option;

public class QuestionGrader {
	public double[] gradeDragDrop(ArrayList<AssessmentDragDrop> submission) {
		// sum of correct choices
		int countCorrect = 0;
		
		// get sum of correct choices
		for (AssessmentDragDrop dd : submission)
			if (dd.getUserOrder() == dd.getDragDrop().getCorrectOrder())
				countCorrect++;
			
		// declare returned object array
		double[] results = new double[2];
		
		// populate entries
		results[0] = countCorrect; // sum of correct choices
		results[1] = (double) countCorrect / (double) submission.size(); // score
		
		return results;
	}
	
	public int[] gradeMultipleChoice(Assessment assessment) {
		// sum of correct options
		int[] results = new int[assessment.getOptions().size()];
		
		int i = 0;
		// get sum of correct choices
		for (Iterator<Option> t = assessment.getOptions().iterator(); t
				.hasNext(); i++) {
			results[i] = t.next().getCorrect();
			i++;
		}
		return results;
	}
}