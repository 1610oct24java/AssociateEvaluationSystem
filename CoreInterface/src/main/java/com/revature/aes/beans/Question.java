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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aes_question")
public class Question implements Serializable {

	private static final long serialVersionUID = 4510024807505207528L;
	@Id
	@Column(name = "question_id")
	@SequenceGenerator(sequenceName = "aes_question_seq", name = "aes_question_seq")
	@GeneratedValue(generator = "aes_question_seq", strategy = GenerationType.SEQUENCE)
	private int questionId;

	@Column(name = "question_text")
	private String questionText;

	
	/////FIGURE OUT MAPPING//////////////////////////////////////////////////////////////////
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "FORMAT_ID")
	private Format format;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "optionId")
	private List<Option> multiChoice;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "dragDropId")
	private List<DragDrop> dragDrops;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "templateQuestion")
	private TemplateQuestion templateQuestion;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "snippetTemplateId")
	private SnippetTemplate snippetTemplate;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_question_tag", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "aes_question_category", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> category;

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

	public TemplateQuestion getTemplateQuestion() {
		return templateQuestion;
	}

	public void setTemplateQuestion(TemplateQuestion templateQuestion) {
		this.templateQuestion = templateQuestion;
	}

	public SnippetTemplate getSnippetTemplate() {
		return snippetTemplate;
	}

	public void setSnippetTemplate(SnippetTemplate snippetTemplate) {
		this.snippetTemplate = snippetTemplate;
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
		result = prime * result + ((templateQuestion == null) ? 0 : templateQuestion.hashCode());
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
		if (templateQuestion == null) {
			if (other.templateQuestion != null)
				return false;
		} else if (!templateQuestion.equals(other.templateQuestion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionText=" + questionText + ", format=" + format
				+ ", multiChoice=" + multiChoice + ", dragDrops=" + dragDrops + ", templateQuestion=" + templateQuestion
				+ ", snippetTemplate=" + snippetTemplate + ", tags=" + tags + ", category=" + category + "]";
	}

	public Question(int questionId, String questionText, Format format, List<Option> multiChoice,
			List<DragDrop> dragDrops, TemplateQuestion templateQuestion, SnippetTemplate snippetTemplate,
			List<Tag> tags, List<Category> category) {
		this();
		this.questionId = questionId;
		this.questionText = questionText;
		this.format = format;
		this.multiChoice = multiChoice;
		this.dragDrops = dragDrops;
		this.templateQuestion = templateQuestion;
		this.snippetTemplate = snippetTemplate;
		this.tags = tags;
		this.category = category;
	}

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

}