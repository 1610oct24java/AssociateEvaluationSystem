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
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
@Entity
@Table(name="AES_TEMPLATE_QUESTION")
public class TemplateQuestion implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = -8227667088089601251L;

	/**
	 * @question The question associated with the class.
	 */
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	/**
	 * @template The template associated with the class.
	 */
	@JoinColumn(name="TEMPLATE_ID")
	private Template template;
	
	/**
	 * @weight A Double value representing the overall weight of a question.
	 */
	@Column (name = "WEIGHT")
	private Double weight;

	public TemplateQuestion()
	{
		super();
	}

	public TemplateQuestion(Question question, Template template, Double weight)
	{
		super();
		this.question = question;
		this.template = template;
		this.weight = weight;
	}

	public Question getQuestion()
	{
		return question;
	}

	public void setQuestion(Question question)
	{
		this.question = question;
	}

	public Template getTemplate()
	{
		return template;
	}

	public void setTemplate(Template template)
	{
		this.template = template;
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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		TemplateQuestion other = (TemplateQuestion) obj;
		if (question == null)
		{
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (template == null)
		{
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (weight == null)
		{
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "TemplateQuestion [question=" + question + ", template=" + template + ", weight=" + weight + "]";
	}
	
}
