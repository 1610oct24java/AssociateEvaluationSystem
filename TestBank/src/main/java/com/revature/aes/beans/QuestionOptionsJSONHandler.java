package com.revature.aes.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

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
		
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		
		QuestionOptionsJSONHandler other = (QuestionOptionsJSONHandler) obj;
		
		//
		
		boolean catDragMultiArray = Objects.equals(categories, other.categories)
								&&	Objects.equals(dragDrops, other.dragDrops)
								&& 	Objects.equals(multiChoice, other.multiChoice); 
		
		boolean formatQuest = Objects.equals(format, other.format)
							&&	Objects.equals(question, other.question);
		
		boolean snippetTags = Objects.equals(snippetTemplate, other.snippetTemplate)
							&&	Objects.equals(tags, other.tags);
		
		return catDragMultiArray && formatQuest && snippetTags;
		
	}

	@Override
	public String toString() {
		return "QuestionOptionsJSONHandler [categories=" + Arrays.toString(categories) + ", dragDrops="
				+ Arrays.toString(dragDrops) + ", format=" + format + ", multiChoice=" + Arrays.toString(multiChoice)
				+ ", question=" + question + ", snippetTemplate=" + snippetTemplate + ", tags=" + Arrays.toString(tags)
				+ "]";
	}

}
