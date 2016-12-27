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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "QUESTION_FORMAT_ID")
	private Format format;

	/**
	 * @options A List representing a selection of options that belong to the
	 *          question.
	 * 
	 *          Note: This is only necessary for specific formats such as
	 *          true/false, multiple choice, multiple select
	 */
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="OPTION_ID")
	private List<Option> options;
	
	/**
	 * @tagList a List of tags that relates to the question.
	 */
	@JsonIgnore
	@ManyToMany(mappedBy="questions",cascade=CascadeType.REMOVE)
	private List<Tag> tags;
	 
	/**
	 * @categories a List of Categories that relates to the question.
	 */
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.REMOVE)
	@JoinTable(
		name="AES_QUESTION_CATEGORY"
		, joinColumns={
			@JoinColumn(name="QUESTION_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CATEGORY_ID")
			}
		)
	private List<Category> categories;
	@JsonIgnore
	@OneToMany(mappedBy="question", cascade=CascadeType.REMOVE)
	private List<DragAndDrop> dragAndDrops;
	@JsonIgnore
	@OneToMany(mappedBy="question", cascade=CascadeType.REMOVE)
	private List<UploadedFile> uploadedFiles;
	@JsonIgnore
	@OneToMany(mappedBy="question", cascade=CascadeType.REMOVE)
	private List<SnippetTemplate> snippetTemplates;
	@JsonIgnore
	@OneToMany(mappedBy="question", cascade=CascadeType.REMOVE)
	private List<TemplateQuestion> templateQuestions = new ArrayList<>();;
	
	public Question()
	{
		super();
	}
	
	public Question(String questionText)
	{
		this();
		this.questionText = questionText;
	}

	public Question(Integer questionId, String questionText, Format format) {
		this(questionText);
		this.questionId = questionId;
		this.format = format;
	}

	public Question(Integer questionId, String questionText, Format format, List<Option> options, List<Category> categoryList, List<Tag> tagList)
	{
		this(questionId,questionText,format);
		this.options = options;
		this.categories = categoryList;
		this.tags = tagList;
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
	
	public void setQuestionId(Integer questionId)
	{
		this.questionId = questionId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<DragAndDrop> getDragAndDrops() {
		return dragAndDrops;
	}

	public void setDragAndDrops(List<DragAndDrop> dragAndDrops) {
		this.dragAndDrops = dragAndDrops;
	}
	
	

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}

	public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
		this.uploadedFiles = uploadedFiles;
	}

	public List<SnippetTemplate> getSnippetTemplates() {
		return snippetTemplates;
	}

	public void setSnippetTemplates(List<SnippetTemplate> snippetTemplates) {
		this.snippetTemplates = snippetTemplates;
	}

	public List<TemplateQuestion> getTemplateQuestions() {
		return templateQuestions;
	}

	public void setTemplateQuestions(List<TemplateQuestion> templateQuestions) {
		this.templateQuestions = templateQuestions;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		if (categories == null)
		{
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
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
		if (tags == null)
		{
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionText=" + questionText + "]";
	}



}