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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "aes_template_question")
public class TemplateQuestion implements Serializable {

	private static final long serialVersionUID = -8227667088089601251L;
	
	@Id
	@Column(name = "template_question_id")
	@SequenceGenerator(sequenceName = "aes_template_question_seq", name = "aes_template_question_seq")
	@GeneratedValue(generator = "aes_template_question_seq", strategy = GenerationType.SEQUENCE)
	private int templateQuestionId;
	
	@Column(name = "weight")
	private int weight;
	
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private Question patternInquiry;

	@Column(name="TEMPLATE_ID")
	Integer template;
	
	public TemplateQuestion() {
		super();
	}

	@Override
	public String toString() {
		return "TemplateQuestion [templateQuestionId=" + templateQuestionId + ", weight=" + weight
				+ ", templateQuestion=" + patternInquiry + ", template=" + template + "]";
	}

	public int getTemplateQuestionId() {
		return templateQuestionId;
	}

	public void setTemplateQuestionId(int templateQuestionId) {
		this.templateQuestionId = templateQuestionId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Question getTemplateQuestion() {
		return patternInquiry;
	}

	public void setTemplateQuestion(Question templateQuestion) {
		this.patternInquiry = templateQuestion;
	}

	public Integer getTemplate() {
		return template;
	}

	public void setTemplate(Integer template) {
		this.template = template;
	}
}
