/****************************************************************
 * Project Name: Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
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
	// TODO update when I get sequence name.
	/**
	 * @questionId The unique Identifier for the Class
	 */
	@Id
	@SequenceGenerator(name = "", sequenceName = "")
	@GeneratedValue(generator = "", strategy = GenerationType.SEQUENCE)
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
	// TODO get Column Name
	@JoinColumn(name = "")
	private Format format;

	/**
	 * @options A List representing a selection of options that belong to the
	 *          question.
	 * 
	 *          Note: This is only necessary for specific formats such as
	 *          true/false, multiple choice, multiple select
	 */
	// TODO Annotation
	private List<Option> options;
	/**
	 * @category
	 */
	private Category category;
	/**
	 * @tag
	 */
	private Tag tag;
	
	private List<Category> categoryList;
	private List<Tag> tagList;
	 

	public Question()
	{
		super();
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




}
