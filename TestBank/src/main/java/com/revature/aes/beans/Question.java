package com.revature.aes.beans;

import java.io.Serializable;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "aes_question")
public class Question implements Serializable {

	private static final long serialVersionUID = 4510024807505207528L;
	@Id
	@Column(name = "QUESTION_ID")
	@SequenceGenerator(sequenceName = "AES_QUESTION_SEQ", name = "AES_QUESTION_SEQ")
	@GeneratedValue(generator = "AES_QUESTION_SEQ", strategy = GenerationType.SEQUENCE)
	private int questionId;

	@Column(name = "QUESTION_TEXT")
	private String questionText;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "QUESTION_FORMAT_ID")
	private Format format;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_TAG", 
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Set<Tag> tags;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_CATEGORY", 
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
	private Set<Category> category;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name="QUESTION_ID")
	private Set<Option> multiChoice;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private Set<DragDrop> dragDrops;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private SnippetTemplate snippetTemplate;


	public Question() {
		super();
	}

	public Question(int questionId, String questionText) {
		this();
		this.questionId = questionId;
		this.questionText = questionText;
	}

	public Question(int questionId, String questionText, Format format) {
		this(questionId, questionText);
		this.format = format;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

	public Set<Option> getMultiChoice() {
		return multiChoice;
	}

	public void setMultiChoice(Set<Option> multiChoice) {
		this.multiChoice = multiChoice;
	}

	public Set<DragDrop> getDragDrops() {
		return dragDrops;
	}

	public void setDragDrops(Set<DragDrop> dragDrops) {
		this.dragDrops = dragDrops;
	}

	public SnippetTemplate getSnippetTemplate() {
		return snippetTemplate;
	}

	public void setSnippetTemplate(SnippetTemplate snippetTemplate) {
		this.snippetTemplate = snippetTemplate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionText=" + questionText + "]";
	}

}