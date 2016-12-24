/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ASSESSMENT_DRAG_DROP")
public class AssessmentDragAndDrop implements Serializable
{
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 4357634270764791488L;

	/**
	 * @dragDropId The DragAndDrop this Class is associated with.
	 *  
	 */
	@ManyToMany
	@JoinColumn(name="DRAG_DROP_ID")
	private DragAndDrop dragDropId;
	
	/**
	 * @assessment The Assessment this Class is associated with.
	 */
	@ManyToMany
	@JoinColumn(name="ASSESSMENT_ID")
	private Assessment assessment;
	
	/**
	 * @userOrder A Numerical value representing the Order the user of a drag and drop assessment.
	 */
	@Column(name="USER_ORDER")
	private Integer userOrder;

	public AssessmentDragAndDrop()
	{
		super();
	}

	public AssessmentDragAndDrop(DragAndDrop dragDropId, Assessment assessment, Integer userOrder)
	{
		this();
		this.dragDropId = dragDropId;
		this.assessment = assessment;
		this.userOrder = userOrder;
	}

	public DragAndDrop getDragDropId()
	{
		return dragDropId;
	}

	public void setDragDropId(DragAndDrop dragDropId)
	{
		this.dragDropId = dragDropId;
	}

	public Assessment getAssessment()
	{
		return assessment;
	}

	public void setAssessment(Assessment assessment)
	{
		this.assessment = assessment;
	}

	public Integer getUserOrder()
	{
		return userOrder;
	}

	public void setUserOrder(Integer userOrder)
	{
		this.userOrder = userOrder;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessment == null) ? 0 : assessment.hashCode());
		result = prime * result + ((dragDropId == null) ? 0 : dragDropId.hashCode());
		result = prime * result + ((userOrder == null) ? 0 : userOrder.hashCode());
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
		AssessmentDragAndDrop other = (AssessmentDragAndDrop) obj;
		if (assessment == null)
		{
			if (other.assessment != null)
				return false;
		} else if (!assessment.equals(other.assessment))
			return false;
		if (dragDropId == null)
		{
			if (other.dragDropId != null)
				return false;
		} else if (!dragDropId.equals(other.dragDropId))
			return false;
		if (userOrder == null)
		{
			if (other.userOrder != null)
				return false;
		} else if (!userOrder.equals(other.userOrder))
			return false;
		return true;
	}
	
	
	
	
}
