	package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "AES_OPTIONS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Option implements Serializable {

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = -2721235710924038934L;
	/**
	 * @optionId the unique Identifier for the Option class
	 */
	@Id
	@GeneratedValue(generator = "AES_OPTION_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_OPTION_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_OPTION_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="700"),
			@Parameter(name="increment_size",value="1")
	})
	@Column(name = "OPTION_ID")
	private int optionId;
	/**
	 * @optionText A String representation of a possible answer for a question.
	 */
	@Column(name = "OPTION_TEXT")
	private String optionText;
	/**
	 * @correct A Integer value representing the correct answer for a question.
	 *          Minimum value is 0, Maximum value is 1. 0 is equivalent to false
	 *          while 1 is equivalent to true.
	 */
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "CORRECT")
	private int correct;
	
	/**
	 * @question The question associated with this class.
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@JoinColumn(name = "QUESTION_ID")
	@JsonIgnore
	private Question question;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "JUSTIFICATION_ID")
	private Justification justification;
	
	public Option() {
		super();
		this.optionId = 0;
	}
	public Option(String optionText, int correct, Question question){
		this();
		this.optionText = optionText;
		this.correct = correct;
		this.question = question;
	}
	
	public Option(Integer optionId, String optionText, int correct) {
		this();
		this.optionId = optionId;
		this.optionText = optionText;
		this.correct = correct;
	}
	
	public Option(int optionId, String optionText, int correct, Question question) {
		this(optionId, optionText, correct);
		this.question = question;
	}

	public Option(String optionText, int correct, Question question, Justification justification) {
		this.optionText = optionText;
		this.correct = correct;
		this.question = question;
		this.justification = justification;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Justification getJustification() {
		return justification;
	}

	public void setJustification(Justification justification) {
		this.justification = justification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + correct;
		result = prime * result + optionId;
		result = prime * result + ((optionText == null) ? 0 : optionText.hashCode());
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
		if (correct != other.correct)
			return false;
		if (optionId != other.optionId)
			return false;
		if (optionText == null) {
			if (other.optionText != null)
				return false;
		} else if (!optionText.equals(other.optionText))
			return false;
		return true;
	}
	@Override
	public String toString() {

		if(question == null){

			return "Option [optionId=" + optionId + ", optionText=" + optionText + ", correct=" + correct
					+ ", question=null ]";

		}

		return "Option [optionId=" + optionId + ", optionText=" + optionText + ", correct=" + correct
				+ ", questionId=" + question.getQuestionId() + ", justification=" + justification + "]";
	}

}

