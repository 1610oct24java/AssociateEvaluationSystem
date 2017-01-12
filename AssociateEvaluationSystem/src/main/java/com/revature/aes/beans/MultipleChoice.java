package com.revature.aes.beans;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/*@Entity
//@DiscriminatorValue("Multiple Choice")
public class MultipleChoice extends Question
{
	private static final long serialVersionUID = -4842113744023608010L;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "optionId")
	private Set<Option> choices;

	public MultipleChoice(int questionId, Format format, Set<Category> questionCategory, String questionText,
			Set<Tag> questionTags, Set<Option> choices)
	{
		super(questionId, format, questionCategory, questionText, questionTags);
		this.choices = choices;
	}

	public MultipleChoice()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public MultipleChoice(int questionId, Format format, Set<Category> questionCategory, String questionText,
			Set<Tag> questionTags)
	{
		super(questionId, format, questionCategory, questionText, questionTags);
	}

	public Set<Option> getChoices()
	{
		return choices;
	}

	public void setChoices(Set<Option> choices)
	{
		this.choices = choices;
	}

	@Override
	public String toString()
	{
		return "MultipleChoice [choices=" + choices + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((choices == null) ? 0 : choices.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultipleChoice other = (MultipleChoice) obj;
		if (choices == null) {
			if (other.choices != null)
				return false;
		} else if (!choices.equals(other.choices))
			return false;
		return true;
	}
}
*/