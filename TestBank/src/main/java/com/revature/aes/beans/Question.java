package com.revature.aes.beans;

import java.io.Serializable;
import java.util.List;
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
	@JoinColumn(name = "QUESTION_FORMAT_ID")
	private Format format;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_TAG", 
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Set<Tag> tags;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "AES_QUESTION_CATEGORY", 
		joinColumns = @JoinColumn(name = "QUESTION_ID"), 
		inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
	private Set<Category> category;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL,mappedBy="question")
	private List<Option> multiChoice;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private Set<DragDrop> dragDrops;	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private SnippetTemplate snippetTemplate;

	public Question() {
		super();
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionText=" + questionText + ", format=" + format
				+ ", multiChoice=" + multiChoice + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((dragDrops == null) ? 0 : dragDrops.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((multiChoice == null) ? 0 : multiChoice.hashCode());
		result = prime * result + questionId;
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
		result = prime * result + ((snippetTemplate == null) ? 0 : snippetTemplate.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (dragDrops == null) {
			if (other.dragDrops != null)
				return false;
		} else if (!dragDrops.equals(other.dragDrops))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (multiChoice == null) {
			if (other.multiChoice != null)
				return false;
		} else if (!multiChoice.equals(other.multiChoice))
			return false;
		if (questionId != other.questionId)
			return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		if (snippetTemplate == null) {
			if (other.snippetTemplate != null)
				return false;
		} else if (!snippetTemplate.equals(other.snippetTemplate))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
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

	public List<Option> getMultiChoice() {
		return multiChoice;
	}

	public void setMultiChoice(List<Option> multiChoice) {
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
	
	
}