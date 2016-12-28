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

@Embeddable
public class AssessmentDragAndDropId implements Serializable {


	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = 389943358618803605L;
	
	@Column(name ="ASSESSMENT_ID")
	private Integer assessementId;
	
	@Column(name="DRAG_DROP_ID")
	private Integer dragDropId;
	
	public AssessmentDragAndDropId() {
		super();
	}


public AssessmentDragAndDropId(Integer assessementId, Integer dragDropId) {
	super();
	this.assessementId = assessementId;
	this.dragDropId = dragDropId;
}


public Integer getAssessementId() {
	return assessementId;
}


public void setAssessementId(Integer assessementId) {
	this.assessementId = assessementId;
}


public Integer getDragDropId() {
	return dragDropId;
}


public void setDragDropId(Integer dragDropId) {
	this.dragDropId = dragDropId;
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((assessementId == null) ? 0 : assessementId.hashCode());
	result = prime * result + ((dragDropId == null) ? 0 : dragDropId.hashCode());
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
	if (assessementId == null) {
		if (other.assessementId != null)
			return false;
	} else if (!assessementId.equals(other.assessementId))
		return false;
	if (dragDropId == null) {
		if (other.dragDropId != null)
			return false;
	} else if (!dragDropId.equals(other.dragDropId))
		return false;
	return true;
}

	
}
