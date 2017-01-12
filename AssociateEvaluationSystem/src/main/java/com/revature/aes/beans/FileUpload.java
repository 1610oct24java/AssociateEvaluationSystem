package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aes_file_upload")
public class FileUpload implements Serializable {
	
	private static final long serialVersionUID = 3082492540225468947L;
	
	@Id
	@Column(name = "file_id")
	@SequenceGenerator(sequenceName = "file_upload_seq", name = "file_upload_seq", allocationSize=1)
	@GeneratedValue(generator = "file_upload_seq", strategy = GenerationType.SEQUENCE)
	private int fileId;

	@Column(name = "file_url")
	private String fileUrl;

	@Column(name = "grade")
	private int grade;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ASSESSMENT_ID")
	private Assessment assessment;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private Question question;

	public FileUpload() {
		super();
	}
	
	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessmentId) {
		this.assessment = assessment;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FileUpload [fileId=" + fileId + ", fileUrl=" + fileUrl + ", grade=" + grade + "]";
	}
	
}