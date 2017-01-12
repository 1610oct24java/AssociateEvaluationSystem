package com.revature.aes.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="QUESTION_FORMAT_ID")
@Table(name = "AES_QUESTION")
public class Question implements Serializable {

	private static final long serialVersionUID = 4510024807505207528L;

	@Id
	@Column(name = "QUESTION_ID")
	@SequenceGenerator(sequenceName = "AES_QUESTION_SEQ", name = "AES_QUESTION_SEQ", allocationSize=1)
	@GeneratedValue(generator = "AES_QUESTION_SEQ", strategy = GenerationType.SEQUENCE)
	private int questionId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "QUESTION_FORMAT_ID")	
	private Format format;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="AES_QUESTION_CATEGORY", joinColumns= @JoinColumn(name="QUESTION_ID"), inverseJoinColumns=@JoinColumn(name="CATEGORY_ID"))
	private Set<Category> questionCategory;

	@Column(name = "QUESTION_TEXT")
	private String questionText;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_TAG", joinColumns = @JoinColumn(name = "QUESTION_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Set<Tag> questionTags;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public Set<Category> getQuestionCategory() {
		return questionCategory;
	}

	public void setQuestionCategory(Set<Category> questionCategory) {
		this.questionCategory = questionCategory;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Set<Tag> getQuestionTags() {
		return questionTags;
	}

	public void setQuestionTags(Set<Tag> questionTags) {
		this.questionTags = questionTags;
	}


}