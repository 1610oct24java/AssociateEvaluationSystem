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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="ASSESSMENT_DRAG_DROP")
//http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/
public class AssessmentDragAndDrop implements Serializable
{
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 4357634270764791488L;


	@EmbeddedId
	private AssessmentDragAndDropId assessmentDragAndDropId = new AssessmentDragAndDropId();
	/**
	 * @userOrder A Numerical value representing the Order the user of a drag and drop assessment.
	 */
	@Column(name="USER_ORDER")
	private Integer userOrder;
	/**
	 * @dragDrop The DragAndDrop this Class is associated with.
	 */
	@ManyToOne
	@JoinColumn(name="fk_dragDrop_id", insertable = false, updatable = false)
	private DragAndDrop dragDrop;
	
	/**
	 * @assessment The Assessment this Class is associated with.
	 */

	@ManyToOne
	@JoinColumn(name="fk_assessment_id", insertable = false, updatable = false)
	private Assessment assessment;

	public AssessmentDragAndDrop()
	{
		super();
	}

	public AssessmentDragAndDrop(DragAndDrop dragDropId, Assessment assessment, Integer userOrder)
	{
		this();
		this.userOrder = userOrder;
	}
	
	public DragAndDrop getDragDrop() {
		return dragDrop;
	}

	public void setDragDrop(DragAndDrop dragDrop) {
		this.dragDrop = dragDrop;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessmentDragAndDropId(AssessmentDragAndDropId assessmentDragAndDropId) {
		this.assessmentDragAndDropId = assessmentDragAndDropId;
	}

	public AssessmentDragAndDrop(AssessmentDragAndDropId assessmentDragAndDropId, Integer userOrder,
			DragAndDrop dragDrop, Assessment assessment) {
		super();
		this.assessmentDragAndDropId = assessmentDragAndDropId;
		this.userOrder = userOrder;
		this.dragDrop = dragDrop;
		this.assessment = assessment;
	}

	public AssessmentDragAndDropId getAssessmentDragAndDropId() {
		return assessmentDragAndDropId;
	}

	public void setAssessmentDradAndDropId(AssessmentDragAndDropId assessmentDragAndDropId) {
		this.assessmentDragAndDropId = assessmentDragAndDropId;
	}

	public Integer getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(Integer userOrder) {
		this.userOrder = userOrder;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessment == null) ? 0 : assessment.hashCode());
		result = prime * result + ((assessmentDragAndDropId == null) ? 0 : assessmentDragAndDropId.hashCode());
		result = prime * result + ((dragDrop == null) ? 0 : dragDrop.hashCode());
		result = prime * result + ((userOrder == null) ? 0 : userOrder.hashCode());
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
		AssessmentDragAndDrop other = (AssessmentDragAndDrop) obj;
		if (assessment == null) {
			if (other.assessment != null)
				return false;
		} else if (!assessment.equals(other.assessment))
			return false;
		if (assessmentDragAndDropId == null) {
			if (other.assessmentDragAndDropId != null)
				return false;
		} else if (!assessmentDragAndDropId.equals(other.assessmentDragAndDropId))
			return false;
		if (dragDrop == null) {
			if (other.dragDrop != null)
				return false;
		} else if (!dragDrop.equals(other.dragDrop))
			return false;
		if (userOrder == null) {
			if (other.userOrder != null)
				return false;
		} else if (!userOrder.equals(other.userOrder))
			return false;
		return true;
	}
	
}
