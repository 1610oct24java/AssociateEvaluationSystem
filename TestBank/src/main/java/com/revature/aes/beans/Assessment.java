package com.revature.aes.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aes_assessment")
public class Assessment implements Serializable {

	private static final long serialVersionUID = -6152668317029130986L;
	@Id
	@Column(name = "assessment_id")
	@SequenceGenerator(sequenceName = "assessment_seq", name = "assessment_seq")
	@GeneratedValue(generator = "assessment_seq", strategy = GenerationType.SEQUENCE)
	private int assessmentId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "grade")
	private int grade;

	@Column(name = "time_limit")
	private int timeLimit;

	@Column(name = "created_timestamp")
	private LocalDateTime createdTimeStamp;

	@Column(name = "finished_timestamp")
	private LocalDateTime finishedTimeStamp;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "template_id")
	private Template template;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_assessment_options", 
		joinColumns = @JoinColumn(name = "assessment_id"), 
		inverseJoinColumns = @JoinColumn(name = "option_id"))
	private List<Option> options;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name="AES_ASSESSMENT_DRAG_DROP")
	private List<AssessmentDragDrop> assessmentDragDrop;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "file_id")
	@JoinTable(name="ASSESSMENT_ID")
	private List<FileUpload> fileUpload;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_snippet_response", 
		joinColumns = @JoinColumn(name = "snippet_template_id"), 
		inverseJoinColumns = @JoinColumn(name = "assessment_id"))
	private List<SnippetTemplate> snippedTemplate;


	public Assessment() {
		super();
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


	public LocalDateTime getCreatedTimeStamp() {
		return createdTimeStamp;
	}


	public void setCreatedTimeStamp(LocalDateTime createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}


	public LocalDateTime getFinishedTimeStamp() {
		return finishedTimeStamp;
	}


	public void setFinishedTimeStamp(LocalDateTime finishedTimeStamp) {
		this.finishedTimeStamp = finishedTimeStamp;
	}


	public Template getTemplate() {
		return template;
	}


	public void setTemplate(Template template) {
		this.template = template;
	}


	public List<Option> getOptions() {
		return options;
	}


	public void setOptions(List<Option> options) {
		this.options = options;
	}


	public List<AssessmentDragDrop> getAssessmentDragDrop() {
		return assessmentDragDrop;
	}


	public void setAssessmentDragDrop(List<AssessmentDragDrop> assessmentDragDrop) {
		this.assessmentDragDrop = assessmentDragDrop;
	}


	public List<FileUpload> getFileUpload() {
		return fileUpload;
	}


	public void setFileUpload(List<FileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}


	public List<SnippetTemplate> getSnippedTemplate() {
		return snippedTemplate;
	}


	public void setSnippedTemplate(List<SnippetTemplate> snippedTemplate) {
		this.snippedTemplate = snippedTemplate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Assessment [assessmentId=" + assessmentId + ", user=" + user + ", grade=" + grade + ", timeLimit="
				+ timeLimit + ", createdTimeStamp=" + createdTimeStamp + ", finishedTimeStamp=" + finishedTimeStamp
				+ ", template=" + template + ", options=" + options + ", assessmentDragDrop=" + assessmentDragDrop
				+ ", fileUpload=" + fileUpload + ", snippedTemplate=" + snippedTemplate + "]";
	}
}
