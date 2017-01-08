
package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "aes_assessment_auth")
public class AssessmentAuth implements Serializable {
	
	private static final long serialVersionUID = -2732479042247683247L;
	
	@Id
	@Column(name = "assessment_auth_id")
	@SequenceGenerator(sequenceName = "aes_assessment_auth_seq", name = "aes_assessment_auth_seq")
	@GeneratedValue(generator = "aes_assessment_auth_seq", strategy = GenerationType.SEQUENCE)
	private int assessmentAuthId;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "url_auth")
	private String urlAuth;
	
	@Column(name = "url_assessment")
	private String urlAssessment;
	
	public AssessmentAuth() {
		super();
	}
	
	public int getAssessmentAuthId() {
		
		return assessmentAuthId;
	}
	
	public void setAssessmentAuthId(int assessmentAuthId) {
		
		this.assessmentAuthId = assessmentAuthId;
	}
	
	public User getUser() {
		
		return user;
	}
	
	public void setUser(User user) {
		
		this.user = user;
	}
	
	public String getUrlAuth() {
		
		return urlAuth;
	}
	
	public void setUrlAuth(String urlAuth) {
		
		this.urlAuth = urlAuth;
	}
	
	public String getUrlAssessment() {
		
		return urlAssessment;
	}
	
	public void setUrlAssessment(String urlAssessment) {
		
		this.urlAssessment = urlAssessment;
	}
	
	@Override
	public String toString() {
		
		return "AssessmentAuth [assessmentAuthId="
				+ assessmentAuthId
				+ ", userId="
				+ user
				+ ", urlAuth="
				+ urlAuth
				+ ", urlAssessment="
				+ urlAssessment
				+ "]";
	}
	
}
