package com.revature.aes.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "AES_QUESTION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question implements Serializable {

	private static final long serialVersionUID = 4510024807505207528L;

	@Id
	@Column(name = "QUESTION_ID")
	@GeneratedValue(generator = "AES_QUESTION_SEQ")
	@GenericGenerator(name="AES_QUESTION_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_QUESTION_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int questionId;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name = "QUESTION_FORMAT_ID")	
	private Format format;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="AES_QUESTION_CATEGORY", joinColumns= @JoinColumn(name="QUESTION_ID"), inverseJoinColumns=@JoinColumn(name="CATEGORY_ID"))
	private Set<Category> questionCategory = new HashSet<>();

	@Column(name = "QUESTION_TEXT")
	private String questionText;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinTable(name = "AES_QUESTION_TAG", joinColumns = @JoinColumn(name = "QUESTION_ID"), inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private Set<Tag> questionTags = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="question")
	private Set<Option> option;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL, mappedBy="question")
	private Set<DragDrop> dragdrop;
	
	/*@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL, mappedBy="question")
	private Set<FileUpload> fileUpload;*/

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="question")
	private Set<SnippetTemplate> snippetTemplates;

	public Question(int questionId, Format format, Set<Category> questionCategory, String questionText,
			Set<Tag> questionTags, Set<Option> multiple, Set<DragDrop> dragdrop)	{//, Set<FileUpload> fileUpload) {
		super();
		this.questionId = questionId;
		this.format = format;
		this.questionCategory = questionCategory;
		this.questionText = questionText;
		this.questionTags = questionTags;
		this.option = multiple;
		this.dragdrop = dragdrop;
//		this.fileUpload = fileUpload;
	}

	public Question() {
		super();
	}

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

	public void addQuestionCategory(Category cat) {
		this.questionCategory.add(cat);
	}
	
	public void removeQuestionCategory(Category cat)
	{
		this.questionCategory.remove(cat);
	}
	
//-----------------------------------------------------------------------------------------------------------------------blah	
	public void removeQuestionOption(Option opt){
		this.option.remove(opt);
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

	public void addQuestionTags(Tag tag) {
		this.questionTags.add(tag);
	}
	
	public void removeQuestionTags(Tag tag) {
		this.questionTags.remove(tag);
	}

	public Set<Option> getOption() {
		return option;
	}

	public void setOption(Set<Option> multiple) {
		this.option = multiple;
	}

	public Set<DragDrop> getDragdrop() {
		return dragdrop;
	}

	public void setDragdrop(Set<DragDrop> dragdrop) {
		this.dragdrop = dragdrop;
	}

/*	public Set<FileUpload> getFileUpload() {
		return fileUpload;
	}*/

/*	public void setFileUpload(Set<FileUpload> fileUpload) {
		this.fileUpload = fileUpload;
	}*/

	public Set<SnippetTemplate> getSnippetTemplates() {
		return snippetTemplates;
	}

	public void setSnippetTemplates(Set<SnippetTemplate> snippetTemplates) {
		this.snippetTemplates = snippetTemplates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dragdrop == null) ? 0 : dragdrop.hashCode());
/*		result = prime * result + ((fileUpload == null) ? 0 : fileUpload.hashCode());*/
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		result = prime * result + ((questionCategory == null) ? 0 : questionCategory.hashCode());
		result = prime * result + questionId;
		result = prime * result + ((questionTags == null) ? 0 : questionTags.hashCode());
		result = prime * result + ((questionText == null) ? 0 : questionText.hashCode());
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
		if (dragdrop == null) {
			if (other.dragdrop != null)
				return false;
		} else if (!dragdrop.equals(other.dragdrop))
			return false;
/*		if (fileUpload == null) {
			if (other.fileUpload != null)
				return false;
		}*/ /*else if (!fileUpload.equals(other.fileUpload))
			return false;*/
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (option == null) {
			if (other.option != null)
				return false;
		} else if (!option.equals(other.option))
			return false;
		if (questionCategory == null) {
			if (other.questionCategory != null)
				return false;
		} else if (!questionCategory.equals(other.questionCategory))
			return false;
		if (questionId != other.questionId)
			return false;
		if (questionTags == null) {
			if (other.questionTags != null)
				return false;
		} else if (!questionTags.equals(other.questionTags))
			return false;
		if (questionText == null) {
			if (other.questionText != null)
				return false;
		} else if (!questionText.equals(other.questionText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", format=" + format + ", questionCategory=" + questionCategory
				+ ", questionText=" + questionText + ", questionTags=" + questionTags + ", multiple=" + option
				+ ", dragdrop=" + dragdrop + "]";// ", fileUpload=" + fileUpload + "]";
	}

}