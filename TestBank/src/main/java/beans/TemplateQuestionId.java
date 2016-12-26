/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/
package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class TemplateQuestionId implements Serializable {
	
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 2633253686033581576L;

	/**
	 * @question The question associated with the class.
	 */
	@Column(name="fk_question_id")
	private Integer questionid;
	
	
	/**
	 * @template The template associated with the class.
	 */
	@Column(name="fk_template_id")
	private Integer templateid;

	public TemplateQuestionId() {
		super();
	}
	
	public TemplateQuestionId(Integer questionid, Integer templateid) {
		super();
		this.questionid = questionid;
		this.templateid = templateid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionid == null) ? 0 : questionid.hashCode());
		result = prime * result + ((templateid == null) ? 0 : templateid.hashCode());
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
		TemplateQuestionId other = (TemplateQuestionId) obj;
		if (questionid == null) {
			if (other.questionid != null)
				return false;
		} else if (!questionid.equals(other.questionid))
			return false;
		if (templateid == null) {
			if (other.templateid != null)
				return false;
		} else if (!templateid.equals(other.templateid))
			return false;
		return true;
	}

	

	
}
