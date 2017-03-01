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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="AES_SNIPPET_TEMPLATE")
public class SnippetTemplate implements Serializable
{

	private static final long serialVersionUID = -8123132627496476715L;
	

	@Id
	@GeneratedValue(generator = "AES_SNIPPET_TEMPLATE_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_SNIPPET_TEMPLATE_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_SNIPPET_TEMPLATE_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	@Column(name = "SNIPPET_TEMPLATE_ID")
	private Integer snippetTemplateId;
	
	@Column(name="FILE_TYPE")
	private String fileType;
	
	@Column(name="SNIPPET_TEMPLATE_URL")
	private String templateUrl;
	
	@Column(name="SOLUTION_URL")
	private String solutionUrl;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="QUESTION_ID")
	@JsonBackReference
	private Question question;

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

	@JsonIgnore
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return "SnippetTemplate [snippetTemplateId=" + snippetTemplateId + ", fileType=" + fileType + ", templateUrl="
				+ templateUrl + ", solutionUrl=" + solutionUrl + ", question=" + question + "]";
	}
}