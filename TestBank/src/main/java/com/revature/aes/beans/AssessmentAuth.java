package com.revature.aes.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AES_ASSESSMENT_AUTH database table.
 * 
 */
@Entity
@Table(name="AES_ASSESSMENT_AUTH")
public class AssessmentAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ASSESSMENT_AUTH_ID")
	private long assessmentAuthId;

	@Column(name="URL_ASSESSMENT")
	private String urlAssessment;

	@Column(name="URL_AUTH")
	private String urlAuth;

	//bi-directional many-to-one association to AesUser
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public AssessmentAuth() {
	}

	public long getAssessmentAuthId() {
		return this.assessmentAuthId;
	}

	public void setAssessmentAuthId(long assessmentAuthId) {
		this.assessmentAuthId = assessmentAuthId;
	}

	public String getUrlAssessment() {
		return this.urlAssessment;
	}

	public void setUrlAssessment(String urlAssessment) {
		this.urlAssessment = urlAssessment;
	}

	public String getUrlAuth() {
		return this.urlAuth;
	}

	public void setUrlAuth(String urlAuth) {
		this.urlAuth = urlAuth;
	}

	public User getUser() {
		return this.user;
	}

	public void setAesUser(User user) {
		this.user = user;
	}

}