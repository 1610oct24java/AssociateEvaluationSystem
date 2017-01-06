package com.revature.aes.beans;

import java.io.Serializable;

public class AssessmentRequest implements Serializable{

	private static final long serialVersionUID = -4368879898192684993L;
	
	private String category;
	private int mcQuestions;
	private int msQuestions;
	private int ddQuestions;
	private int csQuestions;
	private String link;
	private String userEmail;
	//For a future sprint you'd have a timelimit field here
	//As well as getters and setters and adding it to the tostring of course
	public AssessmentRequest() {
		super();
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String email) {
		this.userEmail = email;
	}
	public int getCsQuestions() {
		return csQuestions;
	}
	public void setCsQuestions(int csQuestions) {
		this.csQuestions = csQuestions;
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
	@Override
	public String toString() {
		return "AssessmentRequest [category=" + category + ", mcQuestions=" + mcQuestions + ", msQuestions="
				+ msQuestions + ", ddQuestions=" + ddQuestions + ", csQuestions=" + csQuestions + ", link=" + link
				+ ", userEmail=" + userEmail + "]";
	}
	
}