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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "aes_assessment_drag_drop")
public class AssessmentDragDrop implements Serializable {

	private static final long serialVersionUID = -6980285894791938854L;
	
	@Id
	@Column(name = "assessment_drag_drop_id")
	@GeneratedValue(generator = "AES_ASSESSMENT_DRAG_DROP_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_ASSESSMENT_DRAG_DROP_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_ASSESSMENT_DRAG_DROP_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int assessmentDragDropId;
	
	@Column(name = "user_order")
	private int userOrder;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ASSESSMENT_ID")
	private Assessment assessment;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "drag_drop_id")
	private DragDrop dragDrop;
	
	public AssessmentDragDrop() {
		super();
	}
	
	@Override
	public String toString() {
		return "AssessmentDragDrop [userOrder=" + userOrder + ", assessmentId=" + assessment + ", dragDrop="
				+ dragDrop + "]";
	}
	public int getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(int userOrder) {
		this.userOrder = userOrder;
	}

	public Assessment getAssessmentId() {
		return assessment;
	}

	public void setAssessmentId(Assessment assessmentId) {
		this.assessment = assessmentId;
	}

	public DragDrop getDragDrop() {
		return dragDrop;
	}

	public void setDragDrop(DragDrop dragDrop) {
		this.dragDrop = dragDrop;
	}
	
}
