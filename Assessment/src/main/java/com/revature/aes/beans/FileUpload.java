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

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "aes_file_upload")
public class FileUpload implements Serializable {
	private static final long serialVersionUID = 3082492540225468947L;
	@Id
	@Column(name = "file_id")
	@SequenceGenerator(sequenceName = "file_upload_seq", name = "file_upload_seq")
	@GeneratedValue(generator = "file_upload_seq", strategy = GenerationType.SEQUENCE)
	private int fileId;
	
	@Column(name = "file_url")
	private String fileUrl;
	
	@Column(name = "grade")
	private int grade;
	
	
	@ManyToOne(fetch = FetchType.LAZY) //?
	@JoinColumn(name = "assessment_id")
	private Assessment assessment;
	
	@Column(name = "QUESTION_ID")
	private int questionId;
	
	
	public FileUpload() {
		super();
	}
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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
	
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FileUpload [fileId=" + fileId + ", fileUrl=" + fileUrl + ", grade=" + grade + ", assessment="
				+ assessment + ", questionId=" + questionId + "]";
	}
	
	
}