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

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "assessment_id")
	private Assessment assessmentId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "drag_drop_id")
	private DragDrop dragDropId;

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

	public DragDrop getDragDropId() {
		return dragDropId;
	}

	public void setDragDropId(DragDrop dragDropId) {
		this.dragDropId = dragDropId;
	}

	@Override
	public String toString() {
		return "AssessmentDragDrop [userOrder=" + userOrder + ", assessmentId=" + assessmentId + ", dragDropId="
				+ dragDropId + "]";
	}

	public AssessmentDragDrop(int userOrder, Assessment assessmentId, DragDrop dragDropId) {
		super();
		this.userOrder = userOrder;
		this.assessmentId = assessmentId;
		this.dragDropId = dragDropId;
	}

}
