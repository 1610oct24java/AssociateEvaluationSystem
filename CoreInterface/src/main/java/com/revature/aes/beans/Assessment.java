package com.revature.aes.beans;

import java.io.Serializable;
import java.sql.Timestamp;

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

@Entity
@Table(name = "aes_assessment")

public class Assessment implements Serializable {

	private static final long serialVersionUID = -6152668317029130986L;
	@Id
	@Column(name = "assessment_id")
	@SequenceGenerator(sequenceName = "aes_assessment_seq", name = "aes_assessment_seq")
	@GeneratedValue(generator = "aes_assessment_seq", strategy = GenerationType.SEQUENCE)
	private int assessmentId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "grade")
	private Integer grade;

	@Column(name = "time_limit")
	private int timeLimit;

	@Column(name = "created_timestamp")
	private Timestamp createdTimeStamp;

	@Column(name = "finished_timestamp")
	private Timestamp finishedTimeStamp;

	public Assessment() {
		super();
	}

	@Override
	public String toString() {

		return "Assessment [assessmentId=" + assessmentId + ", user=" + user + ", grade=" + grade + ", timeLimit="
				+ timeLimit + ", createdTimeStamp=" + createdTimeStamp + ", finishedTimeStamp=" + finishedTimeStamp + "]";
	}

	public int getAssessmentId() {

		return assessmentId;

	}

	public void setAssessmentId(int assessmentId) {
		this.assessmentId = assessmentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public Timestamp getFinishedTimeStamp() {
		return finishedTimeStamp;
	}

	public void setFinishedTimeStamp(Timestamp finishedTimeStamp) {
		this.finishedTimeStamp = finishedTimeStamp;
	}

}