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

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="AES_TEMPLATE_QUESTION")
//http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/
@AssociationOverrides({
	@AssociationOverride(name="pk.question",
			joinColumns = @JoinColumn(name="QUESTION_ID")),
	@AssociationOverride(name="pk.templates",
			joinColumns = @JoinColumn(name="TEMPLATE_ID"))
})
public class TemplateQuestion implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = -8227667088089601251L;
	
	/**
	 * @templateQuestionId The Unique identifier for this class (its a composite key)
	 */
	@EmbeddedId
	private TemplateQuestionId tQID = new TemplateQuestionId();
	
	/**
	 * @weight A Double value representing the overall weight of a question.
	 */
	@Column (name = "WEIGHT")
	private Double weight;

	public TemplateQuestion()
	{
		super();
	}

	public TemplateQuestion(Double weight)
	{
		super();
		this.weight = weight;
	}
	
	public TemplateQuestionId getTemplateQuestionId(){
		return tQID;
	}
	public void setTemplateQuestionId(TemplateQuestionId tQID){
		this.tQID = tQID;
	}
	
	@Transient
	public Question getQuestion()
	{
		return getTemplateQuestionId().getQuestion();
	}


	public void setQuestion(Question question)
	{
		getTemplateQuestionId().setQuestion(question);
	}
	@Transient
	public Template getTemplate()
	{
		return getTemplateQuestionId().getTemplate();
	}

	public void setTemplate(Template template)
	{
		getTemplateQuestionId().setTemplate(template);
	}

	public Double getWeight()
	{
		return weight;
	}

	public void setWeight(Double weight)
	{
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tQID == null) ? 0 : tQID.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		TemplateQuestion other = (TemplateQuestion) obj;
		if (tQID == null) {
			if (other.tQID != null)
				return false;
		} else if (!tQID.equals(other.tQID))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TemplateQuestion [tQID=" + tQID + ", weight=" + weight + "]";
	}

}
