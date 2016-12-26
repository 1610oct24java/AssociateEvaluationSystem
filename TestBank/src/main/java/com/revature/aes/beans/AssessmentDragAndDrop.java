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
@AssociationOverrides({
	@AssociationOverride(name="pk.assessment",
			joinColumns = @JoinColumn(name="ASSESSMENT_ID")),
	@AssociationOverride(name="pk.dragDrop",
			joinColumns = @JoinColumn(name="DRAG_DROP_ID"))
})
public class AssessmentDragAndDrop implements Serializable
{
	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 4357634270764791488L;

	@EmbeddedId
	private AssessmentDragAndDropId assessmentDragAndDropId;
	/**
	 * @userOrder A Numerical value representing the Order the user of a drag and drop assessment.
	 */
	@Column(name="USER_ORDER")
	private Integer userOrder;

	@ManyToOne
	@JoinColumn(name="ASSESSMENT_ID", insertable = false, updatable = false)
	private Assessment assessment;

	@ManyToOne
	@JoinColumn(name="DRAG_DROP_ID", insertable = false, updatable = false)
	private DragAndDrop dragDrop;
	
	public AssessmentDragAndDrop()
	{
		super();
		this.assessmentDragAndDropId = new AssessmentDragAndDropId();
	}

	public AssessmentDragAndDrop(DragAndDrop dragDropId, Assessment assessment, Integer userOrder)
	{
		this();
		this.userOrder = userOrder;
	}

	public AssessmentDragAndDropId getAssessmentDragAndDropId() {
		return assessmentDragAndDropId;
	}

	public void setaDDId(AssessmentDragAndDropId assessmentDragAndDropId) {
		this.assessmentDragAndDropId = assessmentDragAndDropId;
	}

	public Integer getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(Integer userOrder) {
		this.userOrder = userOrder;
	}
	
	@Transient
	public Assessment getAssessement(){
		return 	assessmentDragAndDropId.getAssessment();
		}
	
	public void setAssessment(Assessment assessment){
		assessmentDragAndDropId.setAssessment(assessment);
	}
	
	@Transient
	public DragAndDrop getADDIdDragAndDrop(){
		return assessmentDragAndDropId.getDragDrop();
	}
	
	public void setADDIdDragAndDrop(DragAndDrop dragAndDrop){
		assessmentDragAndDropId.setDragDrop(dragAndDrop);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessmentDragAndDropId == null) ? 0 : assessmentDragAndDropId.hashCode());
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
		if (assessmentDragAndDropId == null) {
			if (other.assessmentDragAndDropId != null)
				return false;
		} else if (!assessmentDragAndDropId.equals(other.assessmentDragAndDropId))
			return false;
		if (userOrder == null) {
			if (other.userOrder != null)
				return false;
		} else if (!userOrder.equals(other.userOrder))
			return false;
		return true;
	}
	
}
