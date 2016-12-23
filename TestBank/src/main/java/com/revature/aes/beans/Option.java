/****************************************************************
 * Project Name: Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name  = "AES_OPTIONS")
public class Option implements Serializable {
	

	/**
	 * An auto-generated value used for networking
	 */
	private static final long serialVersionUID = -2721235710924038934L;
	/**
	 * @optionID the unique Identifier for the Option class
	 */
	@Id
	@SequenceGenerator(name = "AES_OPTION_SEQ", sequenceName = "AES_OPTION_SEQ")
	@GeneratedValue(generator = "AES_OPTION_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "OPTION_ID")
	private int optionID;
	/**
	 * @optionText A String representation of a possible answer for a question.
	 */
	private String optionText;
	/**
	 * @correct A boolean value representing the correct answer for a question.
	 */
	private boolean correct;
	
	public Option() {
		super();
	}
	
	public Option(int optionID, String optionText, boolean correct) {
		this();
		this.optionID = optionID;
		this.optionText = optionText;
		this.correct = correct;
	}

	public int getOptionID() {
		return optionID;
	}

	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + optionID;
		result = prime * result + ((optionText == null) ? 0 : optionText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (correct != other.correct)
			return false;
		if (optionID != other.optionID)
			return false;
		if (optionText == null)
		{
			if (other.optionText != null)
				return false;
		} else if (!optionText.equals(other.optionText))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "Option [optionID=" + optionID + ", optionText=" + optionText + ", correct=" + correct + "]";
	}

	
}
