/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AES_ASSESSMENT")
public class Assessment implements Serializable
{
	
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 1795045236864512616L;

	/**
	 * @assessmentId The unique identifier for the Class
	 */
	@Id
	@SequenceGenerator(name = "AES_ASSESSMENT_SEQ", sequenceName = "AES_ASSESSMENT_SEQ")
	@GeneratedValue(generator = "AES_ASSESSMENT_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "ASSESSMENT_ID")
	private Integer assessmentId;
	
	/**
	 * @user A User object representing the User taking an Assessment
	 */
	@ManyToOne //This is many to one as to prepare for when they updated from preAssociate screening to batches.
	@JoinColumn(name="USER_ID")
	private User user;
	/**
	 * @grade The score a user get on the Assessment
	 */
	private Integer grade;
	/**
	 * @timeLimit The duration in minutes the User has to take the test.
	 */
	@Column(name="TIME_LIMIT")
	private Integer timeLimit;
	/**
	 * @createdTimestamp The time the assessment is created 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_TIMESTAMP")
	private Date createdTimestamp;
	/**
	 * @finishedTimestamp The time the assessment is completed.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="FINISHED_TIMESTAMP")
	private Date finishedTimestamp;
	/**
	 * @template A pre-created template to be used as the assessment.  
	 */
	@ManyToOne
	@JoinColumn(name="TEMPLATE_ID")
	private Template template;
	
	/**
	 * @assessmentDragDrops A List of associated drag and drops for the assessment.
	 */
	@OneToMany(mappedBy="assessment")
	private List<AssessmentDragAndDrop> assessmentDragAndDrops;

	@OneToMany(mappedBy="assessments")
	private List<Option> options;

	@OneToMany(mappedBy="assessment")
	private List<UploadedFile> uploadedFiles;

	@ManyToMany(mappedBy="assessments")
	private List<SnippetTemplate> snippetTemplates;
	
	
	public Assessment()
	{
		super();
	}
	public Assessment(Integer assessmentId, User user, Integer grade, Integer timeLimit, Date createdTimestamp,
			Date finishedTimestamp, Template template)
	{
		this();
		this.assessmentId = assessmentId;
		this.user = user;
		this.grade = grade;
		this.timeLimit = timeLimit;
		this.createdTimestamp = createdTimestamp;
		this.finishedTimestamp = finishedTimestamp;
		this.template = template;
	}
	public Integer getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(Integer assessmentId) {
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
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public Date getFinishedTimestamp() {
		return finishedTimestamp;
	}
	public void setFinishedTimestamp(Date finishedTimestamp) {
		this.finishedTimestamp = finishedTimestamp;
	}
	public Template getTemplate() {
		return template;
	}
	public void setTemplate(Template template) {
		this.template = template;
	}
	public List<AssessmentDragAndDrop> getAssessmentDragAndDrops() {
		return assessmentDragAndDrops;
	}
	public void setAssessmentDragAndDrops(List<AssessmentDragAndDrop> assessmentDragAndDrops) {
		this.assessmentDragAndDrops = assessmentDragAndDrops;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}
	public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}
	public List<SnippetTemplate> getSnippetTemplates() {
		return snippetTemplates;
	}
	public void setSnippetTemplates(List<SnippetTemplate> snippetTemplates) {
		this.snippetTemplates = snippetTemplates;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessmentDragAndDrops == null) ? 0 : assessmentDragAndDrops.hashCode());
		result = prime * result + ((assessmentId == null) ? 0 : assessmentId.hashCode());
		result = prime * result + ((createdTimestamp == null) ? 0 : createdTimestamp.hashCode());
		result = prime * result + ((finishedTimestamp == null) ? 0 : finishedTimestamp.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((snippetTemplates == null) ? 0 : snippetTemplates.hashCode());
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		result = prime * result + ((timeLimit == null) ? 0 : timeLimit.hashCode());
		result = prime * result + ((uploadedFiles == null) ? 0 : uploadedFiles.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assessment other = (Assessment) obj;
		if (assessmentDragAndDrops == null) {
			if (other.assessmentDragAndDrops != null)
				return false;
		} else if (!assessmentDragAndDrops.equals(other.assessmentDragAndDrops))
			return false;
		if (assessmentId == null) {
			if (other.assessmentId != null)
				return false;
		} else if (!assessmentId.equals(other.assessmentId))
			return false;
		if (createdTimestamp == null) {
			if (other.createdTimestamp != null)
				return false;
		} else if (!createdTimestamp.equals(other.createdTimestamp))
			return false;
		if (finishedTimestamp == null) {
			if (other.finishedTimestamp != null)
				return false;
		} else if (!finishedTimestamp.equals(other.finishedTimestamp))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (snippetTemplates == null) {
			if (other.snippetTemplates != null)
				return false;
		} else if (!snippetTemplates.equals(other.snippetTemplates))
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (timeLimit == null) {
			if (other.timeLimit != null)
				return false;
		} else if (!timeLimit.equals(other.timeLimit))
			return false;
		if (uploadedFiles == null) {
			if (other.uploadedFiles != null)
				return false;
		} else if (!uploadedFiles.equals(other.uploadedFiles))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
}
