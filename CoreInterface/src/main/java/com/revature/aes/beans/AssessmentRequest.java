package com.revature.aes.beans;

import org.springframework.stereotype.Component;

@Component
public class AssessmentRequest {
	private String category;
	private int mcQuestions;
	private int msQuestions;
	private int ddQuestions;
	private int csQuestions;
	private String link;
	
	public int getCsQuestions() {
		return csQuestions;
	}
	public void setCsQuestions(int csQuestions) {
		this.csQuestions = csQuestions;
	}
	public AssessmentRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getMcQuestions() {
		return mcQuestions;
	}
	public void setMcQuestions(int mcQuestions) {
		this.mcQuestions = mcQuestions;
	}
	public int getMsQuestions() {
		return msQuestions;
	}
	public void setMsQuestions(int msQuestions) {
		this.msQuestions = msQuestions;
	}
	public int getDdQuestions() {
		return ddQuestions;
	}
	public void setDdQuestions(int ddQuestions) {
		this.ddQuestions = ddQuestions;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
