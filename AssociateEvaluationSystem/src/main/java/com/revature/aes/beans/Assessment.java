package com.revature.aes.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "aes_assessment")
public class Assessment implements Serializable
{
	private static final long serialVersionUID = -6152668317029130986L;
	@Id
	@Column(name = "assessment_id")
	@GeneratedValue(generator = "AES_ASSESSMENT_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_ASSESSMENT_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_ASSESSMENT_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int assessmentId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "grade")
	private int grade;

	@Column(name = "time_limit")
	private int timeLimit;

	@Column(name = "created_timestamp")
	private Timestamp createdTimeStamp;

	@Column(name = "finished_timestamp")
	private Timestamp finishedTimeStamp;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "template_id")
	private Template template;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_assessment_options", 
		joinColumns = @JoinColumn(name = "assessment_id"), 
		inverseJoinColumns = @JoinColumn(name = "option_id"))
	private Set<Option> options;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "assessment")
	private Set<AssessmentDragDrop> assessmentDragDrop;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="assessment")
	private Set<FileUpload> fileUpload;

	public Assessment(int assessmentId, User user, int grade, int timeLimit, Timestamp createdTimeStamp,
					  Timestamp finishedTimeStamp, Template template, Set<Option> options,
					  Set<AssessmentDragDrop> assessmentDragDrop, Set<FileUpload> fileUpload) {
		super();
		this.assessmentId = assessmentId;
		this.user = user;
		this.grade = grade;
		this.timeLimit = timeLimit;
		this.createdTimeStamp = createdTimeStamp;
		this.finishedTimeStamp = finishedTimeStamp;
		this.template = template;
		this.options = options;
		this.assessmentDragDrop = assessmentDragDrop;
		this.fileUpload = fileUpload;
	}
	public Assessment(User user, int grade, int timeLimit, Timestamp createdTimeStamp, Timestamp finishedTimeStamp, Template template)
	{
		super();
		this.user = user;
		this.grade = grade;
		this.timeLimit = timeLimit;
		this.createdTimeStamp = createdTimeStamp;
		this.finishedTimeStamp = finishedTimeStamp;
		this.template = template;
	}

	public Assessment() {
		super();
		// TODO Auto-generated constructor stub
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

	public int getGrade() {
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

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Set<Option> getOptions() {
		return options;
	}

	public void setOptions(Set<Option> options) {
		this.options = options;
	}

	public Set<AssessmentDragDrop> getAssessmentDragDrop() {
		return assessmentDragDrop;
	}

	public void setAssessmentDragDrop(Set<AssessmentDragDrop> assessmentDragDrop) {
		this.assessmentDragDrop = assessmentDragDrop;
	}

	public Set<FileUpload> getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(Set<FileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}

	@Override
	public String toString() {
		return "Assessment{" +
				"assessmentId=" + assessmentId +
				", user=" + user +
				", grade=" + grade +
				", timeLimit=" + timeLimit +
				", createdTimeStamp=" + createdTimeStamp +
				", finishedTimeStamp=" + finishedTimeStamp +
				", template=" + template +
				", options=" + options +
				", assessmentDragDrop=" + assessmentDragDrop +
				", fileUpload=" + fileUpload +
				'}';
	}
}
