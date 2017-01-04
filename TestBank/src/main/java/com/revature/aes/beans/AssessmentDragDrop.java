package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "aes_assessment_drag_drop")
public class AssessmentDragDrop implements Serializable {

	private static final long serialVersionUID = -6980285894791938854L;
	
	@Id
	@Column(name = "assessment_drag_drop_id")
	@SequenceGenerator(sequenceName = "aes_assessment_drag_drop_seq", name = "aes_assessment_drag_drop_seq", allocationSize=1)
	@GeneratedValue(generator = "aes_assessment_drag_drop_seq", strategy = GenerationType.SEQUENCE)
	private int assessmentDragDropId;
	
	@Column(name = "user_order")
	private int userOrder;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ASSESSMENT_ID")
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
	
}
