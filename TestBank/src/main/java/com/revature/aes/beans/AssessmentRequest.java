package com.revature.aes.beans;

import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class AssessmentRequest {
    private String category;
    private int mcQuestions;
    private int msQuestions;
    private int ddQuestions;
    private int csQuestions;
    private String link;
    private String userEmail;
    
	public AssessmentRequest() {
		super();
		
	}
	
	public AssessmentRequest(String category, int mcQuestions, int msQuestions, int ddQuestions, int csQuestions,
			String link, String userEmail) {
		super();
		this.category = category;
		this.mcQuestions = mcQuestions;
		this.msQuestions = msQuestions;
		this.ddQuestions = ddQuestions;
		this.csQuestions = csQuestions;
		this.link = link;
		this.userEmail = userEmail;
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
	public int getCsQuestions() {
		return csQuestions;
	}
	public void setCsQuestions(int csQuestions) {
		this.csQuestions = csQuestions;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + csQuestions;
		result = prime * result + ddQuestions;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + mcQuestions;
		result = prime * result + msQuestions;
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		
		
		AssessmentRequest other = (AssessmentRequest) obj;
		
		boolean catCSandDDQuestions = Objects.equals(category, other.category) 
									&& Objects.equals(csQuestions, other.csQuestions)
									&& Objects.equals(ddQuestions, other.ddQuestions);
		
		boolean linkMCandMSQuestions = Objects.equals(link, other.link) 
									&& Objects.equals(mcQuestions, other.mcQuestions)
									&& Objects.equals(msQuestions, other.msQuestions);
		
		return catCSandDDQuestions && linkMCandMSQuestions && Objects.equals(userEmail, other.userEmail);
		

	}
	@Override
	public String toString() {
		return "AssessmentRequest [category=" + category + ", mcQuestions=" + mcQuestions + ", msQuestions="
				+ msQuestions + ", ddQuestions=" + ddQuestions + ", csQuestions=" + csQuestions + ", link=" + link
				+ ", userEmail=" + userEmail + "]";
	}
    
   
    
}
