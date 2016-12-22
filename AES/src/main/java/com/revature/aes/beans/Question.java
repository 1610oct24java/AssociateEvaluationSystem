package com.revature.aes.beans;

import java.util.List;

public class Question {
	private int questionId;
	private int formatId;
	private String questionText;
	private List<Option> options;
	public Question() {
		super();
	}
	public Question(int questionId, int formatId, String questionText, List<Option> options) {
		super();
		this.questionId = questionId;
		this.formatId = formatId;
		this.questionText = questionText;
		this.options = options;
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
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
}
