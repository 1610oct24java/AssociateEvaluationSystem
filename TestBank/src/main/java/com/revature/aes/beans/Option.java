package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "AES_OPTIONS")
public class Option implements Serializable {

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = -2721235710924038934L;
	/**
	 * @optionId the unique Identifier for the Option class
	 */
	@Id
	@SequenceGenerator(name = "AES_OPTION_SEQ", sequenceName = "AES_OPTION_SEQ")
	@GeneratedValue(generator = "AES_OPTION_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "OPTION_ID")
	private Integer optionId;
	/**
	 * @optionText A String representation of a possible answer for a question.
	 */
	@Column(name = "OPTION_TEXT")
	private String optionText;
	/**
	 * @correct A Integer value representing the correct answer for a question.
	 *          Minimun value is 0, Maximun value is 1. 0 is equavalent to false
	 *          while 1 is equavalent to true.
	 */
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "CORRECT")
	private Integer correct;

	/**
	 * @question The question associated with this class.
	 */

	@Column(name = "QUESTION_ID")
	private int questionId;

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Integer getCorrect() {
		return correct;
	}

	public void setCorrect(Integer correct) {
		this.correct = correct;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correct == null) ? 0 : correct.hashCode());
		result = prime * result + ((optionId == null) ? 0 : optionId.hashCode());
		result = prime * result + ((optionText == null) ? 0 : optionText.hashCode());
		result = prime * result + questionId;
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
		Option other = (Option) obj;
		if (correct == null) {
			if (other.correct != null)
				return false;
		} else if (!correct.equals(other.correct))
			return false;
		if (optionId == null) {
			if (other.optionId != null)
				return false;
		} else if (!optionId.equals(other.optionId))
			return false;
		if (optionText == null) {
			if (other.optionText != null)
				return false;
		} else if (!optionText.equals(other.optionText))
			return false;
		if (questionId != other.questionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Option [optionId=" + optionId + ", optionText=" + optionText + ", correct=" + correct + ", questionId="
				+ questionId + "]";
	}

	public Option() {
		super();
		// TODO Auto-generated constructor stub
	}

}
