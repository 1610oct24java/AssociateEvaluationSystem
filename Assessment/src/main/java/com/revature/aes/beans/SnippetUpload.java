package com.revature.aes.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class SnippetUpload implements Serializable{
	
	private static final long serialVersionUID = -5559498337124227184L;
	
	private String code;
	private int questionId;
	private String fileType;
	
	public SnippetUpload() {
		super();	
	}

	public SnippetUpload(String code, int questionId, String fileType) {
		super();
		this.code = code;
		this.questionId = questionId;
		this.fileType = fileType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "SnippetUpload [code=" + code + ", questionId=" + questionId + ", fileType=" + fileType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + questionId;
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
		SnippetUpload other = (SnippetUpload) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		if (questionId != other.questionId)
			return false;
		return true;
	}
}
