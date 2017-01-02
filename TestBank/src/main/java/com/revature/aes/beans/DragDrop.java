package com.revature.aes.beans;

import javax.persistence.*;
import java.io.Serializable;

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