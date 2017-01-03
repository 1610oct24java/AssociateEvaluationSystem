package com.revature.aes.beans;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.stereotype.Component;


/**
 * A helper class used in the conversion of an incoming JSON string into
 * the proper Question Object due to inconsistency of JSON only supporting 
 * Arrays, while the Question Object uses various Collections. 
 */
@Component
public class QuestionOptionsJSONHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1997181371000834912L;
	
	private Category[] categories;
	private DragDrop[] dragDrops;
	private Format format;
	private Option[] multiChoice;
	private Question question;
	private SnippetTemplate snippetTemplate;
	private Tag[] tags;
	
	public QuestionOptionsJSONHandler() {
		super();
	}

	public Category[] getCategories() {
		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	public DragDrop[] getDragDrops() {
		return dragDrops;
	}

	public void setDragDrops(DragDrop[] dragDrops) {
		this.dragDrops = dragDrops;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public Option[] getMultiChoice() {
		return multiChoice;
	}

	public void setMultiChoice(Option[] multiChoice) {
		this.multiChoice = multiChoice;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public SnippetTemplate getSnippetTemplate() {
		return snippetTemplate;
	}

	public void setSnippetTemplate(SnippetTemplate snippetTemplate) {
		this.snippetTemplate = snippetTemplate;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(Tag[] tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(categories);
		result = prime * result + Arrays.hashCode(dragDrops);
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + Arrays.hashCode(multiChoice);
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((snippetTemplate == null) ? 0 : snippetTemplate.hashCode());
		result = prime * result + Arrays.hashCode(tags);
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
		QuestionOptionsJSONHandler other = (QuestionOptionsJSONHandler) obj;
		if (!Arrays.equals(categories, other.categories))
			return false;
		if (!Arrays.equals(dragDrops, other.dragDrops))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (!Arrays.equals(multiChoice, other.multiChoice))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (snippetTemplate == null) {
			if (other.snippetTemplate != null)
				return false;
		} else if (!snippetTemplate.equals(other.snippetTemplate))
			return false;
		if (!Arrays.equals(tags, other.tags))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuestionOptionsJSONHandler [categories=" + Arrays.toString(categories) + ", dragDrops="
				+ Arrays.toString(dragDrops) + ", format=" + format + ", multiChoice=" + Arrays.toString(multiChoice)
				+ ", question=" + question + ", snippetTemplate=" + snippetTemplate + ", tags=" + Arrays.toString(tags)
				+ "]";
	}

}
