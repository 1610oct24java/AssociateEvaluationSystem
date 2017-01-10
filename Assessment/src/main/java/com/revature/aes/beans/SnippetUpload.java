package com.revature.aes.beans;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class SnippetUpload implements Serializable {
	
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
		return "SnippetUpload [code="
				+ code
				+ ", questionId="
				+ questionId
				+ ", fileType="
				+ fileType
				+ "]";
	}
}
