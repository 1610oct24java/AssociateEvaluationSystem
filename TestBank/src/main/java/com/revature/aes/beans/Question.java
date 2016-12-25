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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AES_QUESTION")
public class Question implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated value used for networking.
	 */
	private static final long serialVersionUID = -1601002832615548763L;
	/**
	 * @questionId The unique Identifier for the Class
	 */
	@Id
	@SequenceGenerator(name = "AES_QUESTION_SEQ", sequenceName = "AES_QUESTION_SEQ")
	@GeneratedValue(generator = "AES_QUESTION_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "QUESTION_ID")
	private Integer questionId;

	/**
	 * @questionText A String representation of a question to be asked for an
	 *               evaluation.
	 */
	@Column(name = "QUESTION_TEXT")
	private String questionText;

	
	/**
	 * @format A dependency to determine the format of a question IE(true/false,
	 *         multiple choice, multiple select...)
	 */
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FORMAT_ID")
	private Format format;

	/**
	 * @options A List representing a selection of options that belong to the
	 *          question.
	 * 
	 *          Note: This is only necessary for specific formats such as
	 *          true/false, multiple choice, multiple select
	 */
	@OneToMany(fetch = FetchType.LAZY)
	private List<Option> options;
	
	/**
	 * @categoryList a List of Categories that relates to the question.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Category> categoryList;
	
	/**
	 * @tagList a List of tags that relates to the question.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Tag> tagList;
	 

	public Question()
	{
		super();
	}
	
	public Question(String questionText)
	{
		this();
		this.questionText = questionText;
	}

	public Question(Integer questionId, String questionText, Format format, List<Option> options, List<Category> categoryList, List<Tag> tagList)
	{
		super();
		this.questionId = questionId;
		this.questionText = questionText;
		this.format = format;
		this.options = options;
		this.categoryList = categoryList;
		this.tagList = tagList;
	}

	public int getQuestionId()
	{
		return questionId;
	}

	public void setQuestionId(int questionId)
	{
		this.questionId = questionId;
	}

	public Format getFormat()
	{
		return format;
	}

	public void setFormat(Format format)
	{
		this.format = format;
	}

	public String getQuestionText()
	{
		return questionText;
	}

	public void setQuestionText(String questionText)
	{
		this.questionText = questionText;
	}

	public List<Option> getOptions()
	{
		return options;
	}

	public void setOptions(List<Option> options)
	{
		this.options = options;
	}
	
	public List<Category> getCategoryList()
	{
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList)
	{
		this.categoryList = categoryList;
	}

	public List<Tag> getTagList()
	{
		return tagList;
	}

	public void setTagList(List<Tag> tagList)
	{
		this.tagList = tagList;
	}

	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryList == null) ? 0 : categoryList.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
		result = prime * result + ((tagList == null) ? 0 : tagList.hashCode());
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
		Question other = (Question) obj;
		if (categoryList == null)
		{
			if (other.categoryList != null)
				return false;
		} else if (!categoryList.equals(other.categoryList))
			return false;
		if (format == null)
		{
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (options == null)
		{
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (questionId == null)
		{
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (questionText == null)
		{
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		if (tagList == null)
		{
			if (other.tagList != null)
				return false;
		} else if (!tagList.equals(other.tagList))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Question [questionId=" + questionId + ", questionText=" + questionText + ", format=" + format
				+ ", options=" + options + ", categoryList=" + categoryList
				+ ", tagList=" + tagList + "]";
	}


}