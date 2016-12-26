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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AES_DRAG_DROP")
public class DragAndDrop implements Serializable
{
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 4881131038163109301L;
	
	/**
	 * @dragDropId A unique Identifier for the Class.
	 */
	@Id
	@SequenceGenerator(name = "AES_DRAG_DROP_SEQ", sequenceName = "AES_DRAG_DROP_SEQ")
	@GeneratedValue(generator = "AES_DRAG_DROP_SEQ", strategy = GenerationType.SEQUENCE)
	@Column(name = "DRAG_DROP_ID")
	private Integer dragDropId;
	
	/**
	 * @question The question this class is associated with.
	 */
	@OneToOne
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	/**
	 * @dragDropText A String representation of the selection to be chosen.
	 */
	@Column(name="DRAG_DROP_TEXT")
	private String dragDropText;
	/**
	 * @correctOrder A integer value representing the correct order of placement
	 * for a drag and drop question
	 */
	@Column(name="CORRECT_ORDER")
	private Integer correctOrder;
	
	@OneToMany(mappedBy="dragDrop")
	private List<AssessmentDragAndDrop> assessmentDragAndDrops;
	
	public DragAndDrop()
	{
		super();
	}
	
	public DragAndDrop(Integer dragDropId, Question question, String dragDropText, Integer correctOrder)
	{
		super();
		this.dragDropId = dragDropId;
		this.question = question;
		this.dragDropText = dragDropText;
		this.correctOrder = correctOrder;
	}

	public Integer getDragDropId()
	{
		return dragDropId;
	}

	public void setDragDropId(Integer dragDropId)
	{
		this.dragDropId = dragDropId;
	}

	public Question getQuestion()
	{
		return question;
	}

	public void setQuestion(Question question)
	{
		this.question = question;
	}

	public String getDragDropText()
	{
		return dragDropText;
	}

	public void setDragDropText(String dragDropText)
	{
		this.dragDropText = dragDropText;
	}

	public Integer getCorrectOrder()
	{
		return correctOrder;
	}

	public void setCorrectOrder(Integer correctOrder)
	{
		this.correctOrder = correctOrder;
	}

	public List<AssessmentDragAndDrop> getAssessmentDragAndDrops() {
		return assessmentDragAndDrops;
	}

	public void setAssessmentDragAndDrops(List<AssessmentDragAndDrop> assessmentDragAndDrops) {
		this.assessmentDragAndDrops = assessmentDragAndDrops;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correctOrder == null) ? 0 : correctOrder.hashCode());
		result = prime * result + ((dragDropId == null) ? 0 : dragDropId.hashCode());
		result = prime * result + ((dragDropText == null) ? 0 : dragDropText.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
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
		DragAndDrop other = (DragAndDrop) obj;
		if (correctOrder == null)
		{
			if (other.correctOrder != null)
				return false;
		} else if (!correctOrder.equals(other.correctOrder))
			return false;
		if (dragDropId == null)
		{
			if (other.dragDropId != null)
				return false;
		} else if (!dragDropId.equals(other.dragDropId))
			return false;
		if (dragDropText == null)
		{
			if (other.dragDropText != null)
				return false;
		} else if (!dragDropText.equals(other.dragDropText))
			return false;
		if (question == null)
		{
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
	
	
}
