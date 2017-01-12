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
@Table(name = "AES_DRAG_DROP")
public class DragDrop implements Serializable {

	private static final long serialVersionUID = 7681552840411022561L;
	@Id
	@Column(name = "DRAG_DROP_ID")
	@GeneratedValue(generator = "AES_DRAG_DROP_SEQ", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(name="AES_DRAG_DROP_SEQ", strategy="org.hibernate.id.enhanced.SequenceStyleGenerator", parameters={
			@Parameter(name="sequence_name", value="AES_DRAG_DROP_SEQ"),
			@Parameter(name="optimizer", value="hilo"),
			@Parameter(name="initial_value",value="1"),
			@Parameter(name="increment_size",value="1")
	})
	private int dragDropId;

	@Column(name = "DRAG_DROP_TEXT")
	private String dragDropText;

	@Column(name = "CORRECT_ORDER")
	private int correctOrder;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	private Question questionId;

	public DragDrop() {
		super();
	}

	@Override
	public String toString() {
		return "DragDrop [dragDropId=" + dragDropId + ", dragDropText=" + dragDropText + ", correctOrder="
				+ correctOrder + ", questionId=" + questionId + "]";
	}

	public int getDragDropId() {
		return dragDropId;
	}

	public void setDragDropId(int dragDropId) {
		this.dragDropId = dragDropId;
	}

	public String getDragDropText() {
		return dragDropText;
	}

	public void setDragDropText(String dragDropText) {
		this.dragDropText = dragDropText;
	}

	public int getCorrectOrder() {
		return correctOrder;
	}

	public void setCorrectOrder(int correctOrder) {
		this.correctOrder = correctOrder;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}
}