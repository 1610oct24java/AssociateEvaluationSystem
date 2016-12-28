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
@Table(name = "aes_template_question")
public class TemplateQuestion implements Serializable {

	private static final long serialVersionUID = -8227667088089601251L;
	@Column(name = "weight")
	private int weight;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private Question templateQuestion;

	@Column(name = "template_id")
	private int template;
	
	public TemplateQuestion() {
		super();
	}

	@Override
	public String toString() {
		return "TemplateQuestion [weight=" + weight + ", templateQuestion=" + templateQuestion + ", template="
				+ template + "]";
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Question getTemplateQuestion() {
		return templateQuestion;
	}

	public void setTemplateQuestion(Question templateQuestion) {
		this.templateQuestion = templateQuestion;
	}

	public int getTemplate() {
		return template;
	}

	public void setTemplate(int template) {
		this.template = template;
	}
	
	
}
