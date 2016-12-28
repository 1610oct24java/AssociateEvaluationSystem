package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "aes_assessment_drag_drop")
public class AssessmentDragDrop implements Serializable {
	private static final long serialVersionUID = -6980285894791938854L;
	@Column(name = "user_order ")
	private int userOrder;

	@Column(name = "ASSESSMENT_ID")
	private Assessment assessmentId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drag_drop_id")
	private DragDrop dragDrop;

	public AssessmentDragDrop() {
		super();
	}
	
	@Override
	public String toString() {
		return "AssessmentDragDrop [userOrder=" + userOrder + ", assessmentId=" + assessmentId + ", dragDrop="
				+ dragDrop + "]";
	}
	public int getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(int userOrder) {
		this.userOrder = userOrder;
	}

	public Assessment getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Assessment assessmentId) {
		this.assessmentId = assessmentId;
	}

	public DragDrop getDragDrop() {
		return dragDrop;
	}

	public void setDragDrop(DragDrop dragDrop) {
		this.dragDrop = dragDrop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
