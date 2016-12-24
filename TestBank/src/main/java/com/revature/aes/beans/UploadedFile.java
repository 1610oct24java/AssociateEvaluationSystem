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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name  = "AES_FILE_UPLOAD")
public class UploadedFile implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 7953108647666169046L;
	
	/**
	 * @fileId A unique Identifer for the class.
	 */
	@Id
	@SequenceGenerator(name = "AES_FILE_UPLOAD_SEQ", sequenceName = "AES_FILE_UPLOAD_SEQ")
	@GeneratedValue(generator = "AES_FILE_UPLOAD_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "FILE_ID")
	private Integer fileId;
	
	/**
	 * @fileURL The Uniform Resource Locator to a file being saved.
	 */
	@Column(name="FILE_URL")
	private String fileURL;
	
	/**
	 * @grade A Double value representing the grade an associate gets for the uploaded file.
	 */
	@Column(name="GRADE")
	private Double grade;
	
	/**
	 * @question The Question associated with the class.
	 */
	@OneToMany
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	/**
	 * @assessment The Assessment associated with the class.
	 */
	@ManyToMany
	@JoinColumn(name="ASSESSMENT_ID")
	private Assessment assessment;


	public UploadedFile()
	{
		super();
	}
	
	public UploadedFile(Integer fileId, String fileURL, Double grade, Question question, Assessment assessment)
	{
		super();
		this.fileId = fileId;
		this.fileURL = fileURL;
		this.grade = grade;
		this.question = question;
		this.assessment = assessment;
	}

	public Integer getFileId()
	{
		return fileId;
	}

	public void setFileId(Integer fileId)
	{
		this.fileId = fileId;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public Double getGrade()
	{
		return grade;
	}

	public void setGrade(Double grade)
	{
		this.grade = grade;
	}

	public Question getQuestion()
	{
		return question;
	}

	public void setQuestion(Question question)
	{
		this.question = question;
	}

	public Assessment getAssessment()
	{
		return assessment;
	}

	public void setAssessment(Assessment assessment)
	{
		this.assessment = assessment;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessment == null) ? 0 : assessment.hashCode());
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = prime * result + ((fileURL == null) ? 0 : fileURL.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UploadedFile other = (UploadedFile) obj;
		if (assessment == null)
		{
			if (other.assessment != null)
				return false;
		} else if (!assessment.equals(other.assessment))
			return false;
		if (fileId == null)
		{
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		if (fileURL == null)
		{
			if (other.fileURL != null)
				return false;
		} else if (!fileURL.equals(other.fileURL))
			return false;
		if (grade == null)
		{
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (question == null)
		{
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "UploadedFile [fileId=" + fileId + ", fileURL=" + fileURL + ", grade=" + grade + ", question=" + question
				+ ", assessment=" + assessment + "]";
	}
	
	

	

}
