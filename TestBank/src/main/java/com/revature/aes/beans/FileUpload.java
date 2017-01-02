package com.revature.aes.beans;

import javax.persistence.*;
import java.io.Serializable;

@Entity
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FileUpload [fileId=" + fileId + ", fileUrl=" + fileUrl + ", grade=" + grade + ", assessmentId="
				+ "]";
	}
}