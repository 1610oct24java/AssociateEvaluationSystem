package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "aes_assessment_auth")
public class AssessmentAuth implements Serializable {
	private static final long serialVersionUID = -2732479042247683247L;

	@Id
	@Column(name = "assessment_auth_id")
	@SequenceGenerator(sequenceName = "aes_assessment_auth_seq", name = "aes_assessment_auth_seq")
	@GeneratedValue(generator = "aes_assessment_auth_seq", strategy = GenerationType.SEQUENCE)
	private int assessmentAuthId;
	@Column(name = "user_id")
	private int userId;
	@Column(name = "url_auth")
	private String urlAuth;
	@Column(name = "url_assessment")
	private String urlAssessment;

	public AssessmentAuth(){
		super();
	}

	public AssessmentAuth(int assessmentAuthId, int userId, String urlAuth, String urlAssessment) {
		super();
		this.assessmentAuthId = assessmentAuthId;
		this.userId = userId;
		this.urlAuth = urlAuth;
		this.urlAssessment = urlAssessment;
	}

	public int getAssessmentAuthId() {
		return assessmentAuthId;
	}

	public void setAssessmentAuthId(int assessmentAuthId) {
		this.assessmentAuthId = assessmentAuthId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
		return "AssessmentAuth [assessmentAuthId=" + assessmentAuthId + ", userId=" + userId + ", urlAuth=" + urlAuth
				+ ", urlAssessment=" + urlAssessment + "]";
	}
}

