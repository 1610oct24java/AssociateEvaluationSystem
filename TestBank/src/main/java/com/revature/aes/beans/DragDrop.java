package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name = "aes_drag_drop")
public class DragDrop implements Serializable {

	private static final long serialVersionUID = 7681552840411022561L;
	@Id
	@Column(name = "drag_drop_id")
	@SequenceGenerator(sequenceName = "aes_drag_drop_seq", name = "aes_drag_drop_seq")
	@GeneratedValue(generator = "aes_drag_drop_seq", strategy = GenerationType.SEQUENCE)
	private int dragDropId;

	@Column(name = "drag_drop_text")
	private String dragDropText;

	@Column(name = "correct_order")
	private int correctOrder;

	@Column(name = "question_id")
	private int questionId;

	public DragDrop() {
		super();
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

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DragDrop [dragDropId=" + dragDropId + ", dragDropText=" + dragDropText + ", correctOrder="
				+ correctOrder + ", questionId=" + questionId + "]";
	}
}