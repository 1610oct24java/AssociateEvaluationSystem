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

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AssessmentDragAndDropId implements Serializable {


	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 389943358618803605L;

	/**
	 * @dragDrop The DragAndDrop this Class is associated with.
	 */
	@ManyToOne
	private DragAndDrop dragDrop;
	
	/**
	 * @assessment The Assessment this Class is associated with.
	 */
	@ManyToOne
	private Assessment assessment;

	public AssessmentDragAndDropId() {
		super();
	}

	public AssessmentDragAndDropId(DragAndDrop dragDrop, Assessment assessment) {
		super();
		this.dragDrop = dragDrop;
		this.assessment = assessment;
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

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assessment == null) ? 0 : assessment.hashCode());
		result = prime * result + ((dragDrop == null) ? 0 : dragDrop.hashCode());
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
		AssessmentDragAndDropId other = (AssessmentDragAndDropId) obj;
		if (assessment == null) {
			if (other.assessment != null)
				return false;
		} else if (!assessment.equals(other.assessment))
			return false;
		if (dragDrop == null) {
			if (other.dragDrop != null)
				return false;
		} else if (!dragDrop.equals(other.dragDrop))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AssessmentDragAndDropId [dragDrop=" + dragDrop + ", assessment=" + assessment + "]";
	}
	
}
