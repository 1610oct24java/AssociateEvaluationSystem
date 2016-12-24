/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="AES_SNIPPET_TEMPLATE")
public class SnippetTemplate implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = -8123132627496476715L;
	
	/**
	 * @snippetTemplateId the unique Identifier for the Option class
	 */
	@Id
	@SequenceGenerator(name = "AES_SNIPPET_TEMPLATE_SEQ", sequenceName = "AES_SNIPPET_TEMPLATE_SEQ")
	@GeneratedValue(generator = "AES_SNIPPET_TEMPLATE_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "SNIPPET_TEMPLATE_ID")
	private Integer snippetTemplateId;
	
	/**
	 * @question The question this class is associated with.
	 */
	@ManyToMany
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	/**
	 * @fileType The type of file to be saved to an S3 Bucket. 
	 */
	@Column(name="FILE_TYPE")
	private String fileType;
	
	/**
	 * @snippetTemplateURL A String representation of the Uniform Resource Locator where a 
	 * code snippet created by an associate is located
	 */
	@Column(name="SNIPPET_TEMPLATE_URL")
	private String snippetTemplateURL;
	
	/**
	 * @solutionURL A String representation of the Uniform Resource Locator where a 
	 * code snippet correct solution is located
	 */
	@Column(name="SOLUTION_URL")
	private String solutionURL;
	
	public SnippetTemplate()
	{
		super();
	}

	public SnippetTemplate(Integer snippetTemplateId, Question question, String fileType, String snippetTemplateURL,
			String solutionURL)
	{
		this();
		this.snippetTemplateId = snippetTemplateId;
		this.question = question;
		this.fileType = fileType;
		this.snippetTemplateURL = snippetTemplateURL;
		this.solutionURL = solutionURL;
	}

	public Integer getSnippetTemplateId()
	{
		return snippetTemplateId;
	}

	public void setSnippetTemplateId(Integer snippetTemplateId)
	{
		this.snippetTemplateId = snippetTemplateId;
	}

	public Question getQuestion()
	{
		return question;
	}

	public void setQuestion(Question question)
	{
		this.question = question;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public String getSnippetTemplateURL()
	{
		return snippetTemplateURL;
	}

	public void setSnippetTemplateURL(String snippetTemplateURL)
	{
		this.snippetTemplateURL = snippetTemplateURL;
	}

	public String getSolutionURL()
	{
		return solutionURL;
	}

	public void setSolutionURL(String solutionURL)
	{
		this.solutionURL = solutionURL;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((snippetTemplateId == null) ? 0 : snippetTemplateId.hashCode());
		result = prime * result + ((snippetTemplateURL == null) ? 0 : snippetTemplateURL.hashCode());
		result = prime * result + ((solutionURL == null) ? 0 : solutionURL.hashCode());
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
		SnippetTemplate other = (SnippetTemplate) obj;
		if (fileType == null)
		{
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		if (question == null)
		{
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (snippetTemplateId == null)
		{
			if (other.snippetTemplateId != null)
				return false;
		} else if (!snippetTemplateId.equals(other.snippetTemplateId))
			return false;
		if (snippetTemplateURL == null)
		{
			if (other.snippetTemplateURL != null)
				return false;
		} else if (!snippetTemplateURL.equals(other.snippetTemplateURL))
			return false;
		if (solutionURL == null)
		{
			if (other.solutionURL != null)
				return false;
		} else if (!solutionURL.equals(other.solutionURL))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "SnippetTemplate [snippetTemplateId=" + snippetTemplateId + ", question=" + question + ", fileType="
				+ fileType + ", snippetTemplateURL=" + snippetTemplateURL + ", solutionURL=" + solutionURL + "]";
	}
	
	
	
	
	
	
}
