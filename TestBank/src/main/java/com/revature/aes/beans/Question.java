package com.revature.aes.beans;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FORMAT_ID")
	private Format format;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_TAG", 
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private List<Tag> tags;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_CATEGORY", 
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
	private List<Category> category;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private List<Option> multiChoice;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private List<DragDrop> dragDrops;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private SnippetTemplate snippetTemplate;

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

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public List<Option> getMultiChoice() {
		return multiChoice;
	}

	public void setMultiChoice(List<Option> multiChoice) {
		this.multiChoice = multiChoice;
	}

	public List<DragDrop> getDragDrops() {
		return dragDrops;
	}

	public void setDragDrops(List<DragDrop> dragDrops) {
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
		return "Question [questionId=" + questionId + ", questionText=" + questionText + ", format=" + format
				+ ", tags=" + tags + ", category=" + category + ", multiChoice=" + multiChoice + ", dragDrops="
				+ dragDrops + ", snippetTemplate=" + snippetTemplate + "]";
	}

	public Question() {
		super();
	}
}