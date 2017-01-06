package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AES_SNIPPET_TEMPLATE")
public class SnippetTemplate implements Serializable
{

	private static final long serialVersionUID = -8123132627496476715L;
	

	@Id
	@SequenceGenerator(name = "AES_SNIPPET_TEMPLATE_SEQ", sequenceName = "AES_SNIPPET_TEMPLATE_SEQ", allocationSize=1)
	@GeneratedValue(generator = "AES_SNIPPET_TEMPLATE_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "SNIPPET_TEMPLATE_ID")
	private Integer snippetTemplateId;
	
	@Column(name="FILE_TYPE")
	private String fileType;
	
	@Column(name="SNIPPET_TEMPLATE_URL")
	private String templateUrl;
	
	@Column(name="SOLUTION_URL")
	private String solutionUrl;
	
	@Column(name="QUESTION_ID")
	private Question questionId;

	public SnippetTemplate() {
		super();
	}

	public Integer getSnippetTemplateId() {
		return snippetTemplateId;
	}

	public void setSnippetTemplateId(Integer snippetTemplateId) {
		this.snippetTemplateId = snippetTemplateId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public String getSolutionUrl() {
		return solutionUrl;
	}

	public void setSolutionUrl(String solutionUrl) {
		this.solutionUrl = solutionUrl;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "SnippetTemplate [snippetTemplateId=" + snippetTemplateId + ", fileType=" + fileType + ", templateUrl="
				+ templateUrl + ", solutionUrl=" + solutionUrl + ", questionId=" + questionId + "]";
	}
}