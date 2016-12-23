/****************************************************************
 * Project Name: Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/

package com.revature.aes.beans;

public class Option {
	private int optionID;
	private String optionText;
	private boolean correct;
	private int questionId;
	
	public Option(int optionID, String optionText, boolean correct, int questionId) {
		super();
		this.optionID = optionID;
		this.optionText = optionText;
		this.correct = correct;
		this.questionId = questionId;
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public Option() {
		super();
	}
	
}