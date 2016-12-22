package com.revature.aes.beans;

public class Question {
	private int questionId;
	private int formatId;
	private String questionText;
	
	public Question(int questionId, int formatId, String questionText) {
		super();
		this.questionId = questionId;
		this.formatId = formatId;
		this.questionText = questionText;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getFormatId() {
		return formatId;
	}

	public void setFormatId(int formatId) {
		this.formatId = formatId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
}